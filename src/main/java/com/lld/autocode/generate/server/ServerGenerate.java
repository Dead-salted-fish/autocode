package com.lld.autocode.generate.server;

import java.util.HashMap;
import java.util.Map;

public interface ServerGenerate {
   String getGenerateType();
   void doGenerate(Map<String, Object> model );
}
