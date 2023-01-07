package com.liuesther.springbootmall.constant;

//測試如何去使用這些我們所定義好的 Enum 的值
public class MyTest {
    public static void main(String[] args) { //psvm生成java main方法
        ProductCategory category = ProductCategory.FOOD; // category 裡面的值 存放的就是 FOOD 的值
        String s = category.name();//轉換成是 String 類型的話：使用 Enum 的 name() 方法 去轉換成是一個 String 類型的字串
        System.out.println(s); //FOOD

        //也可以根據字串去搜尋到他所對應的是哪個 Enum 的固定值
        String s2 = "CAR";
        ProductCategory category2 = ProductCategory.valueOf(s2);//根據這個 CAR 的字串 去查看說 有沒有對應到 ProductCategory 裡面的任何一個值的話
        // 如果有的話 他就會將這一個固定值 去儲存在這個 category2 的變數裡面

        //透過 Enum 類型的 name() 方法
        //或是去透過 Enum 類型的 valueOf()
        //那在 Enum 還有 String 字串之間去做轉換
    }
}
