package com.example.demo.src.product;

import com.example.demo.src.product.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ProductDao{
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate =new JdbcTemplate(dataSource);
    }

    public List<GetProductRes> getProducts(){
        String getProductsQuery="SELECT ProductImgUrl,Title ,Emdong ,\n" +
                "       CASE\n" +
                "           WHEN TIMESTAMPDIFF(hour, currenttime, now()) <= 24\n" +
                "               THEN '어제'\n" +
                "           WHEN TIMESTAMPDIFF(hour, currenttime, now()) > 48\n" +
                "               THEN if(TIMESTAMPDIFF(DAY, currenttime, now()) > 7, date_format(currenttime, '%Y-%m-%d'),\n" +
                "                       concat(TIMESTAMPDIFF(DAY, currenttime, now()), '일 전'))\n" +
                "           WHEN TIMESTAMPDIFF(hour, currenttime, now()) < 1\n" +
                "               THEN concat(TIMESTAMPDIFF(MINUTE, currenttime, now()), '분 전')\n" +
                "\n" +
                "           else concat(TIMESTAMPDIFF(hour, currenttime, now()), '시간 전')\n" +
                "           END as currenttime,Price,\n" +
                "       likedcnt ,\n" +
                "       chatcnt  FROM (select ProductImgUrl,Title,Emdong,PRICE,\n" +
                "             GREATEST(PPT.CreatedAt\n" +
                "             ,RefreshedAt) as currenttime,\n" +
                "             count(CASE WHEN Liked = 'Y' THEN 'Y' END) as likedcnt,\n" +
                "             (SELECT count(CASE WHEN CRT.status = 'Y' THEN 'Y' END)\n" +
                "                    FROM Chatting_Room_Table CRT\n" +
                "                    where PPT.Id = CRT.ProductId)\n" +
                "                 as chatcnt\n" +
                "             FROM Product_Page_Table PPT\n" +
                "             JOIN Product_Img_Table PIT on PPT.Id = PIT.ProductId\n" +
                "             JOIN User_Table UT on PPT.UploaderID = UT.Id\n" +
                "             JOIN LatitudeLongitude_DATA on UT.Latitude = LatitudeLongitude_DATA.Latitude\n" +
                "                  and UT.Longitude = LatitudeLongitude_DATA.Longitude\n" +
                "             JOIN Wish_Lists_Table WLT on WLT.ProductId = PPT.Id\n" +
                "      group by ProductImgUrl, Title, Emdong, PPT.CreatedAt, RefreshedAt\n" +
                "     ) product\n" +
                "order by product.currenttime desc";
        return this.jdbcTemplate.query(getProductsQuery,
                (rs,rowNum) -> new GetProductRes(
                        rs.getString("ProductImgUrl"),
                        rs.getString("Title"),
                        rs.getString("Emdong"),
                        rs.getString("currenttime"),
                        rs.getInt("Price"),
                        rs.getLong("likedcnt"),
                        rs.getLong("chatcnt")));
    }

    public List<GetProductRes> getProduct(int CategoryId){
        String getProductQuery="SELECT ProductImgUrl,Title ,Emdong ,\n" +
        "       CASE\n" +
                "           WHEN TIMESTAMPDIFF(hour, currenttime, now()) <= 24\n" +
                "               THEN '어제'\n" +
                "           WHEN TIMESTAMPDIFF(hour, currenttime, now()) > 48\n" +
                "               THEN if(TIMESTAMPDIFF(DAY, currenttime, now()) > 7, date_format(currenttime, '%Y-%m-%d'),\n" +
                "                       concat(TIMESTAMPDIFF(DAY, currenttime, now()), '일 전'))\n" +
                "           WHEN TIMESTAMPDIFF(hour, currenttime, now()) < 1\n" +
                "               THEN concat(TIMESTAMPDIFF(MINUTE, currenttime, now()), '분 전')\n" +
                "           else concat(TIMESTAMPDIFF(hour, currenttime, now()), '시간 전')\n" +
                "           END as currenttime,Price,\n" +
                "       likedcnt ,\n" +
                "       chatcnt  FROM (select ProductImgUrl,Title,Emdong,PRICE,\n" +
                "             GREATEST(PPT.CreatedAt\n" +
                "             ,RefreshedAt) as currenttime,\n" +
                "             count(CASE WHEN Liked = 'Y' THEN 'Y' END) as likedcnt,\n" +
                "             (SELECT count(CASE WHEN CRT.status = 'Y' THEN 'Y' END)\n" +
                "                    FROM Chatting_Room_Table CRT\n" +
                "                    where PPT.Id = CRT.ProductId)\n" +
                "                 as chatcnt\n" +
                "             FROM Product_Page_Table PPT\n" +
                "             JOIN Product_Img_Table PIT on PPT.Id = PIT.ProductId\n" +
                "             JOIN User_Table UT on PPT.UploaderID = UT.Id\n" +
                "             JOIN LatitudeLongitude_DATA on UT.Latitude = LatitudeLongitude_DATA.Latitude\n" +
                "                  and UT.Longitude = LatitudeLongitude_DATA.Longitude\n" +
                "             JOIN Wish_Lists_Table WLT on WLT.ProductId = PPT.Id where CategoryId=?" +
                "      group by ProductImgUrl, Title, Emdong, PPT.CreatedAt, RefreshedAt\n" +
                "     ) product\n" +
                "order by product.currenttime desc";
        int getProductParams=CategoryId;
        return this.jdbcTemplate.query(getProductQuery,
                (rs, rowNum)-> new GetProductRes(
                        rs.getString("ProductImgUrl"),
                        rs.getString("Title"),
                        rs.getString("Emdong"),
                        rs.getString("currenttime"),
                        rs.getInt("Price"),
                        rs.getLong("likedcnt"),
                        rs.getLong("chatcnt")),getProductParams);

    }


   public GetProductDetailRes getProductDetail(int ProductId) {
        String getProductDetailQuery="SELECT ProductImgUrl ,ProfileImageUrl,UserName,Emdong, MannerT,Title,CategoryName,CASE\n" +
                "            WHEN TIMESTAMPDIFF(hour, currenttime, now()) <= 24\n" +
                "                THEN '어제'\n" +
                "            WHEN TIMESTAMPDIFF(hour,currenttime,now())>48\n" +
                "                THEN if(TIMESTAMPDIFF(DAY, currenttime, now()) > 7, date_format(Product.currenttime, '%Y-%m-%d'),\n" +
                "            concat(TIMESTAMPDIFF(DAY, currenttime, now()), '일 전'))\n" +
                "        WHEN TIMESTAMPDIFF(hour, currenttime, now()) < 1\n" +
                "            THEN concat(TIMESTAMPDIFF(MINUTE, currenttime, now()), '분 전')\n" +
                "    ELSE concat(TIMESTAMPDIFF(hour,currenttime,now()),'시간 전')\n" +
                "END as LastTime,Description,Likedcnt,ChatCnt,ViewCount,Price\n" +
                "FROM (SELECT ProductImgUrl,ProfileImageUrl,UserName,Emdong,(SELECT AVG(MTT.C) FROM Manner_Temperature_Table MTT where PPT.UploaderID = MTT.UserId) as MannerT,CategoryName,\n" +
                "            Title,Description,Price,GREATEST(PPT.CreatedAt, RefreshedAt) as currenttime,count(CASE WHEN Liked='Y' THEN 'y' END) as Likedcnt,(SELECT count(CRT.Id) FROM Chatting_Room_Table CRT) as ChatCnt,ViewCount,PPT.Id as ProductId\n" +
                "    FROM Product_Page_Table PPT\n" +
                "    JOIN Wish_Lists_Table WLT on WLT.ProductID=PPT.Id\n" +
                "    JOIN Product_Img_Table PIT on PPT.Id=PIT.ProductId\n" +
                "    JOIN User_Table UT on UT.Id=PPT.UploaderID\n" +
                "    JOIN LatitudeLongitude_DATA on UT.Latitude =LatitudeLongitude_DATA.Latitude and UT.Longitude= LatitudeLongitude_DATA.Longitude\n" +
                "    JOIN Category Ca on PPT.CategoryId = Ca.id\n" +
                "    GROUP BY ProductImgUrl, ProfileImageUrl, UserName, Emdong, CategoryName, Title, Description, Price, GREATEST(PPT.CreatedAt, RefreshedAt), ViewCount,PPT.Id\n" +
                "        )Product WHERE ProductId=?\n";
        int getProductDetailParams=ProductId;
        return this.jdbcTemplate.queryForObject(getProductDetailQuery,
                (rs,rowNum)->new GetProductDetailRes(
                        rs.getString("ProductImgUrl"),
                        rs.getString("ProfileImageUrl"),
                        rs.getString("UserName"),
                        rs.getString("Emdong"),
                        rs.getBigDecimal("MannerT"),
                        rs.getString("Title"),
                        rs.getString("CategoryName"),
                        rs.getString("LastTime"),
                        rs.getString("Description"),
                        rs.getLong("LikedCnt"),
                        rs.getLong("ChatCnt"),
                        rs.getInt("ViewCount"),
                        rs.getInt("Price")),
                getProductDetailParams);

    }
    public int createProduct(PostProductReq postProductReq){
        String createProductQuery="insert into Product_Page_Table(UploaderId,CategoryId,Title,Description,Price) VALUES(?,?,?,?,?)";
        Object[] createProductParams = new Object[]{postProductReq.getUploaderId(),postProductReq.getCategoryId(),postProductReq.getTitle(),postProductReq.getDescription(),postProductReq.getPrice()};
        this.jdbcTemplate.update(createProductQuery,createProductParams);

        String lastInsertIdQuery="select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
    }

    public int createProductImg(PostProductImgReq postProductImgReq){
        String createProductImgQuery="insert into Product_Img_Table(ProductId,ProductImgUrl) VALUES(?,?)";
        Object[] createProductImgParams=new Object[]{postProductImgReq.getProductId(),postProductImgReq.getProductImgUrl()};
        this.jdbcTemplate.update(createProductImgQuery,createProductImgParams);

        String lastInsertIdQuery="select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
    }

}


