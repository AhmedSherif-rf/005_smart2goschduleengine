package com.ntg.common;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/*********************************
 * Copyright (c) 2017 Aalmalky to Present. 
 * All right reserved
 **********************************/

@Retention(RetentionPolicy.RUNTIME)
public @interface ConditionElement {
	
	String column();
	
	String tableName() default "";
	
}
