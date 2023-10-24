package top.woaibocai.bczx.service;


import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;
import top.woaibocai.bczx.model.entity.product.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findCategoryList(Long id);

    void exportData(HttpServletResponse response);

    void importData(MultipartFile file);
}
