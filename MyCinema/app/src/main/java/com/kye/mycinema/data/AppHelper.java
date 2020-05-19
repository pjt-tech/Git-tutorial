package com.kye.mycinema.data;

import com.android.volley.RequestQueue;

public class AppHelper {
    /*
    안드로이드에서 웹을 이용할 때는 thread를 사용해야 하는데 volley 라이브러리를 사용하여 사용자가 요청만 하면
    RequestQueue가 알아서 thread를 만들어서 요청을 하고 요청 값을 받음
    RequestQueue는 클래스를 따로 만들어 그 안에 넣어놓는것을 권장한다고함
     */
    public static RequestQueue requestQueue;
    public static  String host = "boostcourse-appapi.connect.or.kr";
    public static int port = 10000;
}
