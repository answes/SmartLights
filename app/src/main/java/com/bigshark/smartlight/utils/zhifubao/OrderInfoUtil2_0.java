package com.bigshark.smartlight.utils.zhifubao;

public class OrderInfoUtil2_0 {
	public static final String RSA_PRIVATE = "MIIEpQIBAAKCAQEA/PpHb8Zs/nE/yptY9XZmaVUkQUVU+8D4agdq1JhahgkLW0ltIyuZa3LQugc1uifJT" +
            "LlQ/hL/i1wIviPczeG5xPWlL8CQ1JqU7oOdW39XlhpHT9+NmC0uBHuhgAEqrlMUDgNHRTHHIw/9jXAhdbtZY2a1K+5JE4f9RlIsSiOMUkNO+1YOmVboChzCMprZbSsgx+" +
            "XZlIKNQwdK9z5kbW1Qv1i20k4CFtzrCIOlut5m4C2KJUdCE1mIhDWVg/Wd0HUod9jgZuYABjI1G9/pmAgfpNV6zAinGibLhJi1+eGevdj/cJjp34Uw7Zt3K4ajAPO7MKnTW0Q" +
            "2KBbw/sdLHIHKtwIDAQABAoIBAQDDI2ghX2VJ7B+x8JzxUzByRdvegVWpkNPOLgSJEzCYvbdF5mDKRWJl0L4TEWavPut3RGemZQ8793VC/jTflyQOOxH9wMO3S7pgNaQXJSZxwbmKcj4" +
            "tyYP6r0cHj17V832/xWWfiWM7t6T1Ci61OxwboelUhs1VxItpSA+j7MHb3Uke/Ag5yZzUGzQ3k2+aeR2wys59U5ADJVOd7WFVGa9LNsxxRpZvQfZnknWdbioZOAv7jzgs8t7f98GBSKvZ65+j" +
            "tjQm7ADU6LxwnrGo6k2f6lJ9YDc7gLMUVdDqlHYrYFeDDZ4piBmmTxQJfuPPnEejs39pSiK6+bwQbAZGpcahAoGBAP7LtDNeODF40L0eA5y782dAQ1B2gos1AZa+ls4WWGxOFlUibnyoWgerlM5C6" +
            "rMIq4YScqjnhf9q/Bmwazy4CTlkz/0cyhCROEmY7VB247OWQTrGq8xfhry6+lMFpsvqjr2dz/NF34qkujdlxPd26FKqIpD+nVWNNQYgMChjgU4nAoGBAP4sYBWMfTBePdquRP3rYlUTbIQ+tUWcIu" +
            "3zFJk4GZrc9bbolvXzBhkPEyT9HFqC1QYbMKm9XsqJSE25OiZXB8kZCAHlR3jFyNkeBtPQH6d2JzhkGz9PutFpbHMBP1TuJZrBVXZwngWyx4rjixVwogutY4RaFOcSN9prvR" +
            "ObugjxAoGARoFp2PkOhaoP4PUoPLvJq92dgut4TjsZuHP+2QJX74hgJ6jCeB5B43N4VucveOFCTXRXZCPE8ZvSLO2jjwbCDq612y2XQKRetgbqTGTJt40xeak2KvSYHSuET2r1" +
            "7+F1JEVCIOhJ5lyU5vVzXqL5wL4xmHvMX7sWXB6WFOcMBesCgYEA+oEjxbkr3RLU5YUiLBvkUzMMnZ3sLIoBBwGvSH85BR9G8Jl1xztYxDPOS4NGgZI2f4kmwfQIy5bX8vpebFEUNEd" +
            "+U9f0Q/yywgHM+s9Bk1totjQ7gn+DAUdlAke1cnAwEcEACPZfWABml+VBO8VBgD7IOTZLKZ4T/Cp5NC0kAhECgYEAnEerDZ8lvI174wRm5PweT/kX5VDh9AoTCT0Qaqfq2yq4tLRL/awGtc36aSbPY/pOKZNS" +
            "cNTQXDGTD24kJcurHjrbOSk2eESqOas3mZpYWeLfG/eY4eCuVExKwzjoi6zMPmqQj1vTFdtnHqe0z136I7hFKkhQqXNVNq9R3rwyG+Q=\n";


    /**
     * create the order info. 创建订单信息
     *
     */
    public static String getOrderInfo(String price,String orderId) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"2017033106505939\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"guozuchun@126.com\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + orderId + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"骑格订单\"";

        // 商品详情
        orderInfo += "&body=" + "\"骑格订单\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + "http://bike.tt/Pay/ali_payback"
                + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }


    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content
     *            待签名订单信息
     */
    public static String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }


	



}
