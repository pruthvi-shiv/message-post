/*
 * Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.crystalloids.messagepost;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import com.crystalloids.messagepost.RestUtils;
import org.json.JSONObject;

@RunWith(JUnit4.class)
public class MessageTest {

//	@Before
//	public void setUp() {
//		TestUtils.startDatastore();
//	}

	@Test
	public void testSaveGreeting() throws Exception {
		int n = (int) (Math.random() * 100) * (int)(Math.random() * 100);
		JSONObject j = new JSONObject();
		String authorEmail = "test@superawesometest.com";
		String content = "Test post built during the app engine build" + n;
		String title = "Test post " + n;
		String contentReturn = new String ();

		RestUtils.apiPost(authorEmail, content, title);

		j = RestUtils.apiGet(authorEmail, title);
		contentReturn = j.get("content").toString();
		assertEquals(contentReturn, content);
	}

//	@After
//	public void tearDown() {
//		TestUtils.stopDatastore();
//	}
}
