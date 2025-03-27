package com.jjm.cleanspring.infrastructure.security;

//@Component
//public class CustomAccessDeniedHandler implements AccessDeniedHandler {
//    private final HandlerExceptionResolver resolver;
//
//    public CustomAccessDeniedHandler(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
//        this.resolver = resolver;
//    }
//
//    @Override
//    public void handle(HttpServletRequest request,
//                       HttpServletResponse response,
//                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
//        resolver.resolveException(request, response, null, accessDeniedException);
//    }
//}
