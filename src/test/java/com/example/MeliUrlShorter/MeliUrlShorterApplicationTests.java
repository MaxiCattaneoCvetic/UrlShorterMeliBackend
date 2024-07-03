package com.example.MeliUrlShorter;

import com.example.MeliUrlShorter.bussines.url.exception.URLBadRequestException;
import com.example.MeliUrlShorter.bussines.url.model.Url;
import com.example.MeliUrlShorter.bussines.url.service.ShortMeliService;
import com.example.MeliUrlShorter.persistance.IMeliPersistance;
import com.example.MeliUrlShorter.presentation.controller.req.RequestUrl;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class MeliUrlShorterApplicationTests {


	@Autowired
	private  ShortMeliService shortMeliService;

	@Autowired
	private IMeliPersistance meliUrlPersistance;

	private final String baseURL = "http://localhost:8080/api";





	@Test
	@Order(1)
	void getNewShortURL() throws Exception {

		RequestUrl requestUrl = new RequestUrl(
				"http",
				"mercadolibre",
				".com",
				"",
				"/mis/pedidos"
		);
		Url url = shortMeliService.saveUrl(requestUrl);
		Map<String, String> urlHashed = shortMeliService.checkUrl(url.hash());

		Assertions.assertEquals(urlHashed.get("domain"), url.domain());


	}
	@Test
	@Order(2)
	void generateHash() throws Exception {
		String hash = shortMeliService.generateHash("http://mercadolibre.com/mis/pedidos", 6);
		Assertions.assertEquals(hash.length(), 6);


	}

	@Test
	@Order(3)
	void disableURL() throws Exception {

		Url url = new Url(
				"45a21s",
				"http",
				"mercadolibre",
				".com",
				"",
				"/mis/diable",
				true
		);
		meliUrlPersistance.save(url);
		shortMeliService.disableShortUrl(baseURL+url.hash());


		assertThrows(URLBadRequestException.class, () -> shortMeliService.findByHash(url.hash()), "Url not active, please enable it");






	}


	@Test
	@Order(4)
	void enableURL() throws Exception {

		Url url = new Url(
				"45a21s",
				"http",
				"mercadolibre",
				".com",
				"",
				"/mis/diable",
				false
		);
		meliUrlPersistance.save(url);
		shortMeliService.enableShortUrl(baseURL+url.hash());
		Url urlHashed = shortMeliService.findByHash(url.hash());



		Assertions.assertEquals(urlHashed.isActive(), true);


	}





}
