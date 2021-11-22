package hiberprod;

import java.util.List;

public interface ProductDao {
    Product findById(Long id);
    Product findByTitle(String title);
    List<Product> findAll();
    void save(Product product);
    void updateTitleById(Long id, String newTitle);
    void testCaching();

}
