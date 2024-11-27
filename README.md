# 区块链商品交易记录系统
ChainController 接口文档
上链接口
URL: /toChain
方法: POST
请求体: ChainDataBo 对象
   {
     "code": "产品码",
     "privateKey": "私钥",
     "content": "上链内容",
     "processName": "流程名称"
   }
- 响应: JSONObject
   {
     "code": "1",
     "msg": "Trade success"
   }
描述: 将数据上链
对应代码:
   @RequestMapping("/toChain")
   public ResponseEntity<JSONObject> toChain(@RequestBody ChainDataBo chainDataBo) throws Exception {
       // 参数验证
       if ("".equals(chainDataBo.getCode()) || chainDataBo.getCode() == null) {
           jo.setCode("-1");
           jo.setMsg("Code cannot be empty.");
           return new ResponseEntity<JSONObject>(jo, HttpStatus.OK);
       }
       // ... 其他验证代码 ...

       // 构建上链数据
       TChainData tChainData = new TChainData();
       tChainData.setFrom(tCode.getAddress());
       tChainData.setTo("system");
       tChainData.setContent(chainDataBo.getContent());
       // ... 设置其他字段 ...

       // 签名并发送交易
       TradeObject tradeObject = new TradeObject();
       // ... 设置交易对象字段 ...
       Sign.SignatureData signatureData = EthUtils.signMessage(tradeObject.toString(), pri);
       String sign = EthUtils.getSignStr(signatureData);
       tradeObject.setSign(sign);

       // 保存链数据并发送交易
       chainDataDao.save(tChainData);
       String url = "http://" + ip + ":8001/data/trade";
       restTemplate.postForEntity(url, tradeObject, TradeObject.class);

       jo.setCode("1");
       jo.setMsg("Trade success");
       return new ResponseEntity<JSONObject>(jo, HttpStatus.OK);
   }

查询码接口
URL: /queryCode
方法: GET
参数: code (String)
响应: JSONObject
   {
     "code": "1",
     "msg": "Query success",
     "o": {
       "list": [
         {
           "from": "发送地址",
           "to": "接收地址",
           "content": "上链内容",
           "createTime": "创建时间",
           "code": "产品码",
           "productName": "产品名称",
           "processName": "流程名称",
           "chainStatus": "链状态",
           "blockIndex": "区块索引",
           "hashNo": "哈希编号"
         }
       ],
       "product": {
         "productName": "产品名称",
         "productDesc": "产品描述",
         "address": "地址",
         "createTime": "创建时间"
       }
     }
   }
    描述: 根据码查询链上数据
对应代码:
   @RequestMapping("/queryCode")
   public ResponseEntity<JSONObject> queryCode(String code) {
       JSONObject jo = new JSONObject();

       if ("".equals(code) || code == null) {
           jo.setCode("-1");
           jo.setMsg("Code cannot be empty.");
           return new ResponseEntity<JSONObject>(jo, HttpStatus.OK);
       }

       List<TChainData> tChainData = chainDataDao.queryByCode(code);
       if (tChainData.size() == 0) {
           jo.setCode("-1");
           jo.setMsg("No data found.");
           return new ResponseEntity<JSONObject>(jo, HttpStatus.OK);
       }

       String productName = tChainData.get(0).getProductName();
       TProduct tProduct = productDao.queryByName(productName);
       QueryCodeVo queryCodeVo = new QueryCodeVo();
       queryCodeVo.setList(tChainData);
       queryCodeVo.setProduct(tProduct);

       jo.setO(queryCodeVo);
       jo.setCode("1");
       jo.setMsg("Query success");
       return new ResponseEntity<JSONObject>(jo, HttpStatus.OK);
   }

商品管理接口 (ProductController)
 添加产品
URL: /product/add
方法: POST
请求参数:
{
    "productName": "string", // 产品名称（必填）
    "productDesc": "string", // 产品描述（必填）
    "address": "string",     // 以太坊地址（必填）
    "privateKey": "string"   // 私钥（必填）
}
响应:
{
    "code": "1/-1",         // 1成功，-1失败
    "msg": "string",        // 响应信息
    "o": ""                 // 空对象
}
错误信息:
"Product name cannot be empty"
"Product description cannot be empty"
"Address cannot be empty"
"Private key cannot be empty"
"Product name occupied. Choose a new one."
"Address verification failed. Please try again."

查询所有产品
URL: /queryProducts
方法: GET
请求参数: 无
响应:
{
    "code": "1/-1",         // 1成功，-1失败
    "msg": "string",        // 响应信息
    "o": [{                 // 产品列表
        "productName": "string",
        "productDesc": "string",
        "address": "string",
        "createTime": "string"
    }]
}

码管理接口 (CodeController)
创建产品码
URL: /createCode
方法: POST
请求参数:
{
    "privateKey": "string", // 私钥（必填）
    "id": "number"         // 产品ID（必填，非0）
}

响应:
{
    "code": "1/-1",        // 1成功，-1失败
    "msg": "string"        // 响应信息
}
- 错误信息:
"Private key can't be empty"
"id should be a non-zero value."
"Address verification failed"

 查询产品所有码
URL: /queryAllCodeByProductId
方法: POST
请求参数:
{
    "id": "number",        // 产品ID（必填，非0）
    "privateKey": "string" // 私钥（必填）
}
-响应:
{
    "code": "1/-1",        // 1成功，-1失败
    "msg": "string",       // 响应信息
    "o": [{                // 码列表
        "code": "string",
        "createTime": "string",
        "productName": "string",
        "address": "string",
        "imgUrl": "string"
    }]
}

生成二维码
URL: /generateQRCode
方法: GET
请求参数:
code: string (查询参数)
响应:
Content-Type: image/png
Body: PNG图片数据
说明: 生成包含查询URL的二维码图片，尺寸为200x200像素
所有接口（除二维码接口外）都返回标准JSON格式，包含以下字段：
code: "1" 表示成功，"-1" 表示失败
msg: 响应消息
o: 响应数据对象（可能为空）
所有接口都使用 HTTP 状态码 200 (OK) 返回，实际的业务状态由响应中的 code 字段标识。
TBD