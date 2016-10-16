/*
 * Copyright 2002-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

$(document).ready(function() {
    var basePath = document.location.toString();

    $.atmosphere.subscribe(
            basePath + 'pubsub/recent/notifications',
            activityNotificationHandler,
            $.atmosphere.request = { transport: "long-polling" });

    function activityNotificationHandler(response) {
        if (response.status == 200) {
            var data = response.responseBody;
            if (data.length > 0) {
                $('ul').prepend($('<li></li>').text(" Message Received: " + data));
            }
        }
    }

});
