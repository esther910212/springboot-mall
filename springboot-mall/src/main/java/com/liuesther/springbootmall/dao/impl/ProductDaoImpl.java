package com.liuesther.springbootmall.dao.impl;

import com.liuesther.springbootmall.constant.ProductCategory;
import com.liuesther.springbootmall.dao.ProductDao;
import com.liuesther.springbootmall.dto.ProductQuertParams;
import com.liuesther.springbootmall.dto.ProductRequest;
import com.liuesther.springbootmall.model.Product;
import com.liuesther.springbootmall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.reactive.GenericReactiveTransaction;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductDaoImpl implements ProductDao {

    //去注入 NamedParameterJdbcTemplate 這個 bean 下面才能使用
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Override
    public List<Product> getProducts(ProductQuertParams productQuertParams) {//(ProductCategory category, String search)
        String sql="SELECT product_id, product_name, category, image_url, price, stock, description, " +
                "created_date, last_modified_date " +
                "FROM product WHERE 1=1";//沒有傳任何的參數進去 而是直接去把 product table 中的所有商品數據 全部都去給查詢出來
                // WHERE 1=1 最主要的理由是我們想要讓下面的查詢條件可以自由地去拼接在這個 sql 語法的後面

        Map<String, Object> map = new HashMap<>();//創建了一個空的 map

        // 查詢條件
        if(productQuertParams.getCategory() != null){ //category=>productQuertParams.getCategory()
            sql = sql +" AND category = :category";//AND前面一定要有空白鍵 拼接sql才不會有問題
            map.put("category", productQuertParams.getCategory().name());
        }

        if(productQuertParams.getSearch() != null){ //改寫成是productQueryParams.getCategory()那去取得到商品分類的值
            sql = sql +" AND product_name LIKE :search";
            map.put("search","%"+productQuertParams.getSearch()+"%"); //%模糊查詢 一定不能寫在SQL語句 要寫在map拼接
        }
        // 排序
        //實作這種 ORDER BY 的 sql 語法的時候 那只能夠用這種字串拼接的方式 去拼出這個部分的 sql 語句出來 那是不能夠用這種 sql 的變數去實作的
        //因為controller有設預設值，所以這裡不用再做null檢查
        //拼接這種 sql 語句的時候 一定要記得在 前、後 分別去預留一個空白鍵
        sql = sql +" ORDER BY "+productQuertParams.getOrderBy() + " "+ productQuertParams.getSort();

        // 分頁
        sql = sql +" LIMIT :limit OFFSET :offset";
        map.put("limit",productQuertParams.getLimit());
        map.put("offset",productQuertParams.getOffset());
        //根據前端傳過來的 limit 還有 offset 的值 在查詢資料庫中的數據的時候 去對這些商品的數據進行分頁

        List<Product> productList = namedParameterJdbcTemplate.query(sql,map,new ProductRowMapper());

        return productList;
    }

    @Override
    public Product getProductById(Integer productId) {
        String sql="SELECT product_id, product_name, category, image_url, price, stock, description, " +
                "created_date, last_modified_date " +
                "FROM product WHERE product_id = :productId"; //冒號:java變數

        // new 一個新的 map 出來，將這個 sql 中的參數 productId 給傳進去
        Map<String,Object> map = new HashMap<>();
        map.put("productId",productId);

        //第一個參數就填 sql 的變數、第二個參數是 map 的變數、那 query 方法要傳三個參數轉換數據的 RowMapper
        //那所以我們要再去寫一個 product 的 RowMapper 出來 那才可以將我們從資料庫中所查詢出來的商品數據 去轉換成是 Java object
        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());

        //加上一個判斷 productList有值 才取得第一個 product 的值傳回去
        if(productList.size() > 0){
            return productList.get(0);
        }else{
            return null;
        }
    }


    @Override
    public Integer createProduct(ProductRequest productRequest) {
        //透過這個 INSERT sql 把這些數據都給塞到資料庫裡面
        String sql = "INSERT INTO product (product_name, category, image_url, price, stock, " +
                "description, created_date, last_modified_date) " +
                "VALUES (:productName, :category, :imageUrl, :price, :stock, :description, :createdDate, :lastModifiedDate)";

        //創建了一個 map 然後把前端所傳過來的 productRequest 中的參數 一個一個的去加到這個 map 裡面
        Map<String, Object> map = new HashMap<>();
        map.put("productName", productRequest.getProductName());
        map.put("category",productRequest.getCategory().toString());//.toString()：enum轉字串
        map.put("imageUrl",productRequest.getImageUrl());
        map.put("price",productRequest.getPrice());
        map.put("stock",productRequest.getStock());
        map.put("description",productRequest.getDescription());

        //new 了一個 Date 出來 去記錄當下的時間點
        Date now = new Date();
        //把這個當下的時間 當成是這個商品的創建時間 以及最後修改的時間 那把這兩個參數的值 也去加到 map 裡面
        map.put("createdDate",now);
        map.put("lastModifiedDate",now);

        //------------------------------------------------------以上設定要傳入sql參數的值

        //使用 KeyHolder 去儲存資料庫自動生成的 productId
        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        int productId = keyHolder.getKey().intValue();

        return productId;
    }


    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {
        // 帶入前端傳入的值(冒號：java變數)
        // UPDATE sql 裡面 也會去更新了 last_modified_date 這個欄位的值
        String sql = "UPDATE product SET product_name=:productName, category=:category, image_url=:imageUrl, " +
                "price=:price, stock=:stock, description=:description, last_modified_date=:lastModifiedDate " +
                "WHERE product_id=:productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId",productId);

        map.put("productName",productRequest.getProductName());
        map.put("category",productRequest.getCategory().toString());
        map.put("imageUrl",productRequest.getImageUrl());
        map.put("price",productRequest.getPrice());
        map.put("stock",productRequest.getStock());
        map.put("description",productRequest.getDescription());

        map.put("lastModifiedDate",new Date());

        //去使用 namedParameterJdbcTemplate 的 update 方法去執行這一條 sql 去修改這個商品的數據了
        namedParameterJdbcTemplate.update(sql,map);
    }


    @Override
    public void deleteProductById(Integer productId) {
        String sql="DELETE FROM product WHERE product_id=:productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId",productId);

        namedParameterJdbcTemplate.update(sql,map);
    }
}
