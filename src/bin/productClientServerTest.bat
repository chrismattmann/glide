java -Dglide.productHandlers=glide.product.handlers.TestProductHandler;glide.product.handlers.HardCodedDBHandler -classpath ".;..\..\lib\Prism-MW-2.0.jar;..\..\lib\glide.jar;" glide.product.test.ProductClientTest --query %1