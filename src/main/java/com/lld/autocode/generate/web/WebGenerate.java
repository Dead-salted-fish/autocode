package com.lld.autocode.generate.web;

import java.util.Map;

public interface WebGenerate {
   String getGenerateType();
   void doGenerate(Map<String, Object> model );
}
