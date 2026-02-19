package myself.custom.asset.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI assetOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Asset Manager API")
                        .description("資產管理後端 API 文件 — 包含試算、記帳、行事曆、日記、飲食、運動與系統設定等功能。")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Asset Manager")
                                .url("https://github.com/tibame201020/asset_backend_app")));
    }
}
