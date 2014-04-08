package com.pgrela.wlunch.restaurants;

import static org.fest.assertions.Assertions.assertThat;

import java.io.IOException;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.pgrela.wlunch.SpringContext;
import com.pgrela.wlunch.utils.Downloader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringContext.class})
public class PiwpawRestaurantTest extends  AbstractTestNGSpringContextTests {

        @Mock
        private Downloader downloader;

        @InjectMocks
        @Autowired
        private PiwpawRestaurant piwpawRestaurant;

        @BeforeMethod
        public void before(){
            MockitoAnnotations.initMocks(this);
        }

        @Test
        public void shouldFormatMenu() throws IOException {
            //given

            //when
            Menu menu = piwpawRestaurant.getMenu();

            //then
            assertThat(menu.toString()).contains("karkówka");
        }
}
