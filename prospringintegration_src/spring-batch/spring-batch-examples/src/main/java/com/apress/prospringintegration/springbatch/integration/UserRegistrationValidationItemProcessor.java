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

package com.apress.prospringintegration.springbatch.integration;

import com.apress.prospringintegration.springbatch.UserRegistration;
import org.apache.commons.lang.StringUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;

@Component("userRegistrationValidationProcessor")
public class UserRegistrationValidationItemProcessor
        implements ItemProcessor<UserRegistration, UserRegistration> {
    private Collection<String> states;

    public UserRegistrationValidationItemProcessor() {
        this.states = Arrays.asList(
                ("AL AK AS AZ AR CA CO CT DE DC FM " +
                        "FL GA GU HI ID IL IN IA KS KY LA ME MH MD " +
                        "MA MI MN MS MO MT NE NV NH NJ NM NY NC ND " +
                        "MP OH OK OR PW PA PR RI SC SD TN TX UT " +
                        "VT VI VA WA WV WI WY").split(" "));
    }

    private String stripNonNumbers(String input) {
        String output = StringUtils.defaultString(input);
        StringBuffer numbersOnly = new StringBuffer();
        for (char potentialDigit : output.toCharArray()) {
            if (Character.isDigit(potentialDigit)) {
                numbersOnly.append(potentialDigit);
            }
        }
        return numbersOnly.toString();
    }

    private boolean isTelephoneValid(String telephone) {
        return !StringUtils.isEmpty(telephone) && telephone.length() == 10;
    }

    private boolean isZipCodeValid(String zip) {
        return !StringUtils.isEmpty(zip) && ((zip.length() == 5) || (zip.length() == 9));
    }

    private boolean isValidState(String state) {
        return states.contains(StringUtils.defaultString(state).trim());
    }

    public UserRegistration process(UserRegistration input)
            throws Exception {
        String zipCode = stripNonNumbers(input.getZip());
        String telephone = stripNonNumbers(input.getPhoneNumber());
        String state = StringUtils.defaultString(input.getState());

        if (isTelephoneValid(telephone) && isZipCodeValid(zipCode) && isValidState(state)) {
            input.setZip(zipCode);
            input.setPhoneNumber(telephone);
            System.out.println("input is valid, returning");
            return input;
        }

        System.out.println("Returning null");
        return null;
    }
}
