package hiberprod;

import org.hibernate.Session;

import java.util.List;

public class ProductDaoImplement implements ProductDao {
    private SessionFactoryUtils sessionFactoryUtils;

    public ProductDaoImplement(SessionFactoryUtils sessionFactoryUtils) {
        this.sessionFactoryUtils = sessionFactoryUtils;
    }


    @Override
    public Product findById(Long id) {
        try (Session session = new SessionFactoryUtils().getSession()) {
            session.beginTransaction();
            Product product = session.get(Product.class, id);
            session.getTransaction().commit();
            return product;
        }
    }

    @Override
    public Product findByTitle(String title) {
        try (Session session = new SessionFactoryUtils().getSession()) {
            session.beginTransaction();
            Product product = session
                    .createQuery("select product from Product product where product.title = :title",
                            Product.class)
                    .setParameter("title", title)
                    .getSingleResult();
            session.getTransaction().commit();
            return product;
        }
    }

    @Override
    public List<Product> findAll() {
        try (Session session = sessionFactoryUtils.getSession()) {
            session.beginTransaction();
            List<Product> products = session.createQuery("select p from Product p").getResultList();
            session.getTransaction().commit();
            return products;
        }
    }

    @Override
    public void save(Product product) {
        try (Session session = sessionFactoryUtils.getSession()) {
            session.beginTransaction();
            session.saveOrUpdate(product);
            session.getTransaction().commit();
        }

    }

    @Override
    public void updateTitleById(Long id, String newTitle) {
        try (Session session = sessionFactoryUtils.getSession()) {
            session.beginTransaction();
            Product product = session.get(Product.class, id);
            product.setTitle(newTitle);
            session.getTransaction().commit();
        }
    }

    @Override
    public void testCaching() {
        try (Session session = sessionFactoryUtils.getSession()) {
            session.beginTransaction();
            session.get(Product.class, 1L);
            session.get(Product.class, 1L);
            session.get(Product.class, 1L);
            session.getTransaction().commit();
        }
        try (Session session = sessionFactoryUtils.getSession()) {
            session.beginTransaction();
            session.get(Product.class, 1L);
            session.get(Product.class, 1L);
            session.get(Product.class, 1L);
            session.getTransaction().commit();
        }
    }
}
