/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.pacificsoft.microservices.favorita.controllers;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.math.BigDecimal;
import java.util.Collection;
import net.pacificsoft.microservices.favorita.models.Features;
import net.pacificsoft.microservices.favorita.repository.FeaturesRepository;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = FeatureController.class)
@AutoConfigureMockMvc(secure=false)
public class FeatureTest2 {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @MockBean
    private FeaturesRepository featuresRepository;

    Features f1 = new Features();
    
    @Test
    public void testList() throws Exception {
        assertThat(this.featuresRepository).isNotNull();
        mockMvc.perform(MockMvcRequestBuilders.get("/features"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("mensaje"))
                .andExpect(MockMvcResultMatchers.view().name("mensaje"))
                .andExpect(content().string(Matchers.containsString("Spring Framework Guru")))
                .andDo(print());
    }

    @Test
    public void testShowProduct() throws Exception {
        assertThat(this.featuresRepository).isNotNull();
        when(featuresRepository.getOne(1L)).thenReturn(f1);

        MvcResult result= mockMvc.perform(get("/features/{id}/", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("productshow"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("product"))
                .andExpect(model().attribute("f1", hasProperty("id", is(1))))
                .andExpect(model().attribute("f1", hasProperty("mensaje", is("m")))).andReturn();


        MockHttpServletResponse mockResponse=result.getResponse();
        assertThat(mockResponse.getContentType()).isEqualTo("text/html;charset=UTF-8");

        Collection<String> responseHeaders = mockResponse.getHeaderNames();
        assertNotNull(responseHeaders);
        assertEquals(1, responseHeaders.size());
        assertEquals("Check for Content-Type header", "Content-Type", responseHeaders.iterator().next());
        String responseAsString=mockResponse.getContentAsString();
        assertTrue(responseAsString.contains("Spring Framework Guru"));

        verify(featuresRepository, times(1)).findById(1L);
        verifyNoMoreInteractions(featuresRepository);
    }


}