package codesquad.airdnb.domain.accommodation.controller;

import codesquad.airdnb.domain.accommodation.dto.additionals.FloorPlanData;
import codesquad.airdnb.domain.accommodation.dto.additionals.LocationData;
import codesquad.airdnb.domain.accommodation.dto.request.AccoCreateRequest;
import codesquad.airdnb.domain.accommodation.dto.response.AccoContentResponse;
import codesquad.airdnb.domain.accommodation.service.AccoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.sql.Time;
import java.util.List;

@WebMvcTest(AccoController.class)
class AccoControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccoService accoService;

    private final String urlPrefix = "/api/host";

    private final AccoCreateRequest exampleCreateRequest = new AccoCreateRequest(
            1L,
            "test",
            "house",
            1000L,
            "test description",
            Time.valueOf("16:00:00"),
            Time.valueOf("11:00:00"),
            new LocationData("KR", "AnYang", "Seoul", "Songpa", "MoonJung",
                    "100000동 100004호", "05794", 11.111, 22.222),
            new FloorPlanData(2, 3, 5, 6, 7),
            List.of("1111", "22222", "33333", "44444", "55555"),
            List.of(1L, 2L, 3L, 4L, 5L));

    private final AccoContentResponse exampleContentResponse = AccoContentResponse.builder()
            .id(1L)
            .hostId(1L)
            .title("test")
            .placeCategory("house")
            .price(1000L)
            .description("test description")
            .checkInTime(Time.valueOf("16:00:00"))
            .checkOutTime(Time.valueOf("11:00:00"))
            .locationData(new LocationData("KR", "AnYang", "Seoul", "Songpa", "MoonJung",
                    "100000동 100004호", "05794", 11.111, 22.222))
            .floorPlanData(new FloorPlanData(2, 3, 5, 6, 7))
            .imageUrls(List.of("1111", "22222", "33333", "44444", "55555"))
            .amenities(List.of(1L, 2L, 3L, 4L, 5L))
            .build();

    @Test
    void 알맞은_형식으로_숙소_등록을_하면_200_상태코드를_반환한다() throws Exception {
        // given
        String url = urlPrefix + "/accommodations";
        String requestJson = objectMapper.writeValueAsString(exampleCreateRequest);
        given(accoService.create(exampleCreateRequest)).willReturn(exampleContentResponse);

        // when & then
        mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk());
    }

    @Test
    void 숙소_아이디로_해당_아이디의_숙소_상세정보를_요청하면_200_상태코드를_반환한다() throws Exception {
        // given
        Long accoId = 1L;
        String url = urlPrefix + "/accommodations/" + accoId;
        given(accoService.get(accoId)).willReturn(exampleContentResponse);

        // when
        ResultActions result = mockMvc.perform(get(url));

        // then
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void 호스트_아이디로_해당_호스트가_등록한_숙소_리스트를_요청하면_200_상태코드를_반환한다() throws Exception {
        // given
        Long hostId = 1L;
        String requestParam = "?hostId=";
        String url = urlPrefix + "/accommodations" + requestParam + hostId;

        SimpleAccommodation exSimpleAccommodation = new SimpleAccommodation(1L, "test", "test.com");
        AccoListResponse response = new AccoListResponse(List.of(exSimpleAccommodation));
        given(accoService.getList(hostId)).willReturn(response);

        // when
        ResultActions result = mockMvc.perform(get(url));

        // then
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}