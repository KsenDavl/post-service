package com.example.postservice.integration.controller;

import com.example.postservice.dto.request.NewPostItemDto;
import com.example.postservice.dto.request.PostItemRequestDto;
import com.example.postservice.entity.PostItem;
import com.example.postservice.enums.PostItemStatus;
import com.example.postservice.enums.PostItemType;
import com.example.postservice.integration.AbstractIntegrationTest;
import com.example.postservice.repository.PostItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.util.UriComponentsBuilder;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(scripts = "classpath:data/01-init_test_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@EnableSpringDataWebSupport
class PostItemControllerTest extends AbstractIntegrationTest {

    private final static long POST_ITEM_ID = 7;
    private final static PostItemType TYPE = PostItemType.SMALL_PARCEL;
    private final static int RECEIVER_INDEX = 426054;
    private final static String RECEIVER_ADDRESS = "Pear st, 32";
    private final static String RECEIVER_NAME = "Stephen King";

    private final static int INDEX = 428034;

    @Autowired
    private PostItemRepository postItemRepository;

    private final HttpStatus OK_STATUS = HttpStatus.OK;

    @Test
    void shouldReturn201WhenRegisterNewItem() throws Exception {
        NewPostItemDto dto = new NewPostItemDto(TYPE,  RECEIVER_INDEX, RECEIVER_ADDRESS, RECEIVER_NAME, INDEX);

        int sizeBeforeInsert = postItemRepository.findAll().size();

        mockMvc.perform(post(UriComponentsBuilder.fromPath("/api/v1/post-item/new").toUriString())
                        .contentType(APPLICATION_JSON)
                        .content(toJson(dto)))
                .andExpect(status().is(HttpStatus.CREATED.value()));

        int sizeAfterInsert = postItemRepository.findAll().size();
        assertEquals(sizeBeforeInsert + 1, sizeAfterInsert);
    }

    @Test
    void shouldReturnOkWhenReceivingPostItem() throws Exception {
        PostItemRequestDto dto = new PostItemRequestDto(POST_ITEM_ID, RECEIVER_INDEX);

        PostItem postItemBeforeUpdate = postItemRepository.findById(POST_ITEM_ID).get();

        mockMvc.perform(post(UriComponentsBuilder.fromPath("/api/v1/post-item/in").toUriString())
                        .contentType(APPLICATION_JSON)
                        .content(toJson(dto)))
                .andExpect(status().is(OK_STATUS.value()));

        PostItem postItemAfterUpdate = postItemRepository.findById(POST_ITEM_ID).get();

        assertNotEquals(postItemBeforeUpdate.getStatus(), postItemAfterUpdate.getStatus());
        assertEquals(PostItemStatus.DELIVERED, postItemAfterUpdate.getStatus());
    }

    @Test
    void shouldReturnOkWhenDispatchingPostItem() throws Exception{
        long itemId = 5l;
        PostItemRequestDto dto = new PostItemRequestDto(itemId, 438034);

        PostItem postItemBeforeUpdate = postItemRepository.findById(itemId).get();

        mockMvc.perform(post(UriComponentsBuilder.fromPath("/api/v1/post-item/out").toUriString())
                        .contentType(APPLICATION_JSON)
                        .content(toJson(dto)))
                .andExpect(status().is(OK_STATUS.value()));

        PostItem postItemAfterUpdate = postItemRepository.findById(itemId).get();

        assertNotEquals(postItemBeforeUpdate.getStatus(), postItemAfterUpdate.getStatus());
        assertEquals(PostItemStatus.IN_TRANSIT, postItemAfterUpdate.getStatus());
    }

    @Test
    void shouldReturn_TrackingRecords() throws Exception {
        mockMvc.perform(get(UriComponentsBuilder.fromPath("/api/v1/post-item/tracking/" + POST_ITEM_ID).toUriString()))
                .andExpect(status().is(OK_STATUS.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.records", hasSize(3)));
    }

    @Test
    void shouldReturnOkWhenReceivingByAddressee() throws Exception{
        long itemId = 9l;
        PostItem postItemBeforeUpdate = postItemRepository.findById(itemId).get();

        mockMvc.perform(post(UriComponentsBuilder.fromPath("/api/v1/post-item/receiving/" + itemId).toUriString()))
                                .andExpect(status().is(OK_STATUS.value()));

        PostItem postItemAfterUpdate = postItemRepository.findById(itemId).get();

        assertNotEquals(postItemBeforeUpdate.getStatus(), postItemAfterUpdate.getStatus());
        assertEquals(PostItemStatus.RECEIVED, postItemAfterUpdate.getStatus());
    }
}