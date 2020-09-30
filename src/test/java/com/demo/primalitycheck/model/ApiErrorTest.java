package com.demo.primalitycheck.model;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.test.impl.GetterTester;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ApiError")
class ApiErrorTest {

    @Test
    @DisplayName("Should have a getter for each field")
    public void gettersExist() {
        Validator validator = ValidatorBuilder.create()
                .with(new GetterMustExistRule())
                .with(new GetterTester())
                .build();

        PojoClass pojo = PojoClassFactory.getPojoClass(ApiError.class);
        validator.validate(pojo);
    }
}