package com.alkemy.challenge.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.util.StringUtils.trimAllWhitespace;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.function.Try;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.AbstractPageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.alkemy.challenge.model.PersonajeModel;
import com.alkemy.challenge.services.PersonajeService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import mapper.PersonajeMapperImpl;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = PersonajeEndpoint.class)

public class PersonajeEndpointTest {
	/*
	 * @Autowired private WebApplicationContext wac;
	 */
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private PersonajeService personajeService;
	@Mock
	Pageable pag = Mockito.mock(PageRequest.class);

	@InjectMocks
	private PersonajeEndpoint controller;
	@InjectMocks
	PersonajeMapperImpl mapper;
	
	@Nested
	@DisplayName("Finding a characters")
	class FindingACharacters {
		
		private PersonajeModel p;
		@BeforeEach
		void setUp() {
			objectMapper.setSerializationInclusion(JsonInclude.Include.USE_DEFAULTS);
			mockMvc = MockMvcBuilders.standaloneSetup(controller)
					.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
					.setViewResolvers(new ViewResolver() {
						@Override
						public View resolveViewName(String viewName, Locale locale) throws Exception {
							return new MappingJackson2JsonView();
						}
						
					})
					.build();
			
			p = new PersonajeModel();
			p.setId(1l);
			p.setNombre("juan");
			p.setEdad("55");
		}

		@Nested
		@DisplayName("When Characters content Is Found")
		class WhenCharactersContentIsFound {

			@Test
			@DisplayName("get all characters")
			public void testGetCharactersAll() throws Exception {
				ArrayList<PersonajeModel> myCharacters = new ArrayList<>();
				myCharacters.add(p);
				given(personajeService.findAll()).willReturn(myCharacters);

				MvcResult mvcResult = mockMvc.perform(get("/characters/all").contentType("application/json"))
						.andExpect(status().isOk()).andReturn();

				String actualResponse = mvcResult.getResponse().getContentAsString();
				String expectedResponse = objectMapper.writeValueAsString(mapper.map(myCharacters));

				Assertions.assertEquals(trimAllWhitespace(expectedResponse), actualResponse.replaceAll("\\s+", ""));// #Junit5
				assertThat(actualResponse, is(equalTo(trimAllWhitespace(expectedResponse)))); // #Hamcrest
			}
			
			@Test
			@DisplayName("Get one character")
			public void getOneCharacter() throws Exception{
				Long id = 1l ;
				Optional<PersonajeModel> pOpt = Optional.of(p);
				
				given(personajeService.findOne(id)).willReturn(pOpt);
				MvcResult mvcResult = mockMvc.perform(get("/characters/"+id).contentType("application/json"))
						.andExpect(status().isOk()).andReturn();

				String actualResponse = mvcResult.getResponse().getContentAsString();
				String expectedResponse = objectMapper.writeValueAsString(mapper.personajeToPersonajeDTO(p));
				
				assertThat(trimAllWhitespace(actualResponse), is(equalTo(trimAllWhitespace(expectedResponse))));
				
			} 
			
			@Test
			@DisplayName("Search characters for words")
			public void searchCharactersForWords() throws Exception{
				//nombre, edad, peso, historia, pageable, false
				pag = PageRequest.of(0,2);
				
				Boolean withDeleted = false;
				String nameKey = "juan";
				ArrayList<PersonajeModel> myCharacters = new ArrayList<>();
				myCharacters.add(p);
				
				/*try (MockedStatic<PageRequest> mockP = Mockito.mockStatic(PageRequest.class)){
					 mockP.when(() -> PageRequest.of(0,1))
	                   .thenReturn(pag);
				}*/
				System.out.println(myCharacters.size());
				given(personajeService.findAll(nameKey,null,null,null,pag,withDeleted)).willReturn(myCharacters);
				MvcResult mvcResult = mockMvc.perform(get("/characters/search")
						.contentType("application/json")
						.param("nombre",nameKey)
						.param("page", "0")
                        .param("size", "2"))
						.andExpect(status().isOk()).andReturn();
				
			}
			
			
			@Test
			@DisplayName("Get deleted characters")
			public void getDeletedCharacters() throws Exception{
				Boolean withDeleted = true;
				ArrayList<PersonajeModel> myCharacters = new ArrayList<>();
				myCharacters.add(p);
				
				given(personajeService.findAll(withDeleted)).willReturn(myCharacters);

				MvcResult mvcResult = mockMvc.perform(get("/characters/deleted").contentType("application/json"))
						.andExpect(status().isOk()).andReturn();

				String actualResponse = mvcResult.getResponse().getContentAsString();
				String expectedResponse = objectMapper.writeValueAsString(mapper.map(myCharacters));

				assertThat(actualResponse, is(equalTo(trimAllWhitespace(expectedResponse))));
			}
			
			@Test
			@DisplayName("Get not deleted characters")
			public void getNotDeletedCharacters() throws Exception{
				Boolean withDeleted = false;
				ArrayList<PersonajeModel> myCharacters = new ArrayList<>();
				myCharacters.add(p);
				
				given(personajeService.findAll(withDeleted)).willReturn(myCharacters);

				MvcResult mvcResult = mockMvc.perform(get("/characters/details").contentType("application/json"))
						.andExpect(status().isOk()).andReturn();

				String actualResponse = mvcResult.getResponse().getContentAsString();
				String expectedResponse = objectMapper.writeValueAsString(mapper.map(myCharacters));

				assertThat(actualResponse, is(equalTo(trimAllWhitespace(expectedResponse))));
			}

		}

		@Nested

		@DisplayName("When Characters have No Content")
		class WhenCharactersHaveNotFound {
			@Test
			@DisplayName("return HTTP status No Content of All characters")
			public void returnHttpStatusNoContentOfAllCharacters() throws Exception {
				ArrayList<PersonajeModel> myCharacters = new ArrayList<>();
				given(personajeService.findAll()).willReturn(myCharacters);
				mockMvc.perform(get("/characters/all").contentType("application/json"))
						.andExpect(status().isNoContent());
				
			}

		}

	}

	
}
