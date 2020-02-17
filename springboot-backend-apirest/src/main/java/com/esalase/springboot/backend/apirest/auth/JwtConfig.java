package com.esalase.springboot.backend.apirest.auth;

public class JwtConfig {
	public static final String LLAVE_SECRETA = "alguna.clave.secreta.123456";
	
	public static final String RSA_PRIVATE = "-----BEGIN RSA PRIVATE KEY-----\r\n" + 
			"MIIEowIBAAKCAQEAtoN3DfSpkcwYuIauko/reLwqJhajqQQSsKk4w8MnrReZJAqX\r\n" + 
			"yGXqASEqR2pafvzbplpWOa0drUBddexqHHDrgUHjVdXJ++vWhPMPWrfqPNTIf8T5\r\n" + 
			"LVx/XPv1tGkq0KKZqjzT7T7FrHIhZ9Jgu5St0U/NFoKSuZ4kh4F39W98RnUOo0cY\r\n" + 
			"ys2yxajhTyK24w0iXDy4KdiRdiL3nPsRtvNGzCQjAkn5DKXiWyJh7T1Pw1sRJ5tH\r\n" + 
			"kWSNzzjeVZXOyvMcU1uzmmyydpXo80hjrwMVvJxLfe3UUKKq2z7pg9mmsM600tZz\r\n" + 
			"1zcQWhmN41MbZjPOQ+pgECtm17fdwjzWFB4v/wIDAQABAoIBAGXL03ZkNMPN6yfY\r\n" + 
			"brvuK66ufG+8BtI5JFeGOtPtIE8Jr2+LvPaX6TlyDwvABPN5AuW40wjeRoQgN0CT\r\n" + 
			"eOeeD6yLXEm+sSaidoa2rqSxmaMOJzokWbp0MdFh8rwKQEgXuM9bl/RmI4i4K2ul\r\n" + 
			"yjA1j/EDqmbYsw92ZfdyPb8VzGHa62zrdKhe8O7L61vRscR6T0rIZMxwOJSBbLr6\r\n" + 
			"zJbv/AEgImKUyc5te+Dq7U/X9eFaB4g6vL5v6gEnEvmF6a1wQkuAWSV4sCg25RUC\r\n" + 
			"e3OFcJR7frm62b0XGFlRugcuQKvfZqt/fTaMq5T8gKJcJfIiSOqjZKzAb8FlW/NY\r\n" + 
			"/U0MQPkCgYEA8rtP7NUteFfeO+cw6Qabi4jWCmDSP//G6JN1KWUGPGCyTXz14wBp\r\n" + 
			"2hcMi0jxn0lkyO5H5UdqN0JE00NlZ4a9yw+2+f6u2khsdm29h16wOac4/mPZrRTV\r\n" + 
			"tpdlXjCfJhD/1SFdqqf8TFuEkSZfGYHvdp7JKAxQ/TP1+eMv8fKjQ80CgYEAwH18\r\n" + 
			"FKVceyEankrS0QEmDsZK7UJo4sChyo3Ju5G+vaOlorZS7YYbnijgwdCcsbFeGm7b\r\n" + 
			"2tp/g5xf4C1gqAL4iSIQZIt1pAL/sLMcRYqxHMv0kuzsRZnvquZxdJJAVmDKMtqW\r\n" + 
			"ow6YC3CbGNSJGncPL2rUiPQtsfAzOc6AXicrjvsCgYArJ+p57Rz8fILim6Qyih41\r\n" + 
			"nMqRZc1723n999mo1nFZYsfQxxrglT48rERqrzlmXKIyp0rZFO1pjbq25RCua2Jr\r\n" + 
			"LAwYPpPP83h1ReWgn9EtLvIPKKmXAFFO+XPoXzSsi/Ewb5I7uUo+OTdEp1HZMyTk\r\n" + 
			"Li8Nk8MCga6GE0J+/ejRMQKBgCKRJkwq2e2+pyIP+8XOt//lOiVbTKyTvTo7tiN5\r\n" + 
			"68/waFlM9p1y/NitLJeBl/INEQIcMF0f6dA/r7P5Uic5yVoPBPSZWrnmU/ZuY8BV\r\n" + 
			"RLMQKU6NP2zMVQ7SrUMq1GTuosD5Ih4MC9/aC/AskqZvBQoV3C7v8pjGstxJqiG+\r\n" + 
			"VQMrAoGBAOJ1BgS087efoUByRVtEq4eyf27kBYFw/7X6eEEFj3OqQjCtXX3uMyS5\r\n" + 
			"KFizzRRyBdeHeO31QElvrg0kSaqVzwH7WaThQy7aNH9fXKIt7dYqyiQW2p/WUO5O\r\n" + 
			"aKEhU/YGmJ6IwQXK1BOCNRs6Jx8fxUli17REm1d1c6xmlzpCn6Rx\r\n" + 
			"-----END RSA PRIVATE KEY-----";
	
	public static final String RSA_PUBLIC = "-----BEGIN PUBLIC KEY-----\r\n" + 
			"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtoN3DfSpkcwYuIauko/r\r\n" + 
			"eLwqJhajqQQSsKk4w8MnrReZJAqXyGXqASEqR2pafvzbplpWOa0drUBddexqHHDr\r\n" + 
			"gUHjVdXJ++vWhPMPWrfqPNTIf8T5LVx/XPv1tGkq0KKZqjzT7T7FrHIhZ9Jgu5St\r\n" + 
			"0U/NFoKSuZ4kh4F39W98RnUOo0cYys2yxajhTyK24w0iXDy4KdiRdiL3nPsRtvNG\r\n" + 
			"zCQjAkn5DKXiWyJh7T1Pw1sRJ5tHkWSNzzjeVZXOyvMcU1uzmmyydpXo80hjrwMV\r\n" + 
			"vJxLfe3UUKKq2z7pg9mmsM600tZz1zcQWhmN41MbZjPOQ+pgECtm17fdwjzWFB4v\r\n" + 
			"/wIDAQAB\r\n" + 
			"-----END PUBLIC KEY-----";

}
