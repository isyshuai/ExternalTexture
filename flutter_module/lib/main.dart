
import 'dart:ui';

import 'package:flutter/material.dart';
import 'package:flutter_module/external/toast/Toast.dart';
import 'package:vector_math/vector_math_64.dart' as Vector;
import 'external/ExTextureWidget.dart';
void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: MyHomePage(title: window.defaultRouteName),
    );
  }
}
class MyHomePage extends StatefulWidget {
  MyHomePage({Key key, this.title}) : super(key: key);

  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}
var img="bbb";
var gifImg="fff";
var drawType=DrawType.center;
var currentIndex=1;
var imgUrl="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1606373040028&di=8ba95c56fab1093b37ea98580d9c2d83&imgtype=0&src=http%3A%2F%2Fa4.att.hudong.com%2F27%2F67%2F01300000921826141299672233506.jpg";
class _MyHomePageState extends State<MyHomePage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: Text(widget.title),
        ),
        body:Container(child: Flow(
          delegate:TestFlowDelegate(margin: EdgeInsets.all(10.0)),
          children: [
            Container(child: ExTextureWidget(img: img,width: 50, height: 50,drawType: drawType,),color: Colors.blueGrey,),
            Container(child: ExTextureWidget(img: img,width: 50, height: 150,drawType: drawType,),color: Colors.blueGrey,),
            Container(child: ExTextureWidget(img: img,width: 50, height: 50,drawType: drawType,),color: Colors.blueGrey,),
            Container(child: ExTextureWidget(img: img,width: 150, height: 150,drawType: drawType,),color: Colors.blueGrey,),
            Container(child: ExTextureWidget(img: img,width: 50, height: 50,drawType: drawType,),color: Colors.blueGrey,),
            Container(child: ExTextureWidget(img: img,width: 50, height: 50,drawType: drawType,),color: Colors.blueGrey,),
            Container(child: ExTextureWidget(img: img,width: 50, height: 50,drawType: drawType,),color: Colors.blueGrey,),
            Container(child: ExTextureWidget(img: img,width: 150, height: 150,drawType: drawType,),color: Colors.blueGrey,),
            Container(child: ExTextureWidget(img: img,width: 50, height: 50,drawType: drawType,),color: Colors.blueGrey,),
            Container(child: ExTextureWidget(img: img,width: 150, height: 150,drawType: drawType,),color: Colors.blueGrey,),
            Container(child: ExTextureWidget(img: img,width: 50, height: 50,drawType: drawType,),color: Colors.blueGrey,),
            Container(child: ExTextureWidget(img: img,width: 150, height: 50,drawType: drawType,),color: Colors.blueGrey,),
            Container(child: ExTextureWidget(img: img,width: 200, height: 200,drawType: drawType,),color: Colors.blueGrey,),
            Container(child: ExTextureWidget(img: img,width: 50, height: 50,drawType: drawType,),color: Colors.blueGrey,),
            Container(child: ExTextureWidget(img: img,width: 50, height: 50,drawType: drawType,),color: Colors.blueGrey,),
            Container(child: ExTextureWidget(img: img,width: 50, height: 50,drawType: drawType,),color: Colors.blueGrey,),
            Container(child: ExTextureWidget(img: window.defaultRouteName=="openGl 绘制纹理"?img:imgUrl,width: 150, height: 150,drawType: drawType,),color: Colors.blueGrey,),
            Container(child: ExTextureWidget(img: window.defaultRouteName=="openGl 绘制纹理"?img:imgUrl,width: 150, height: 50,drawType: drawType,),color: Colors.blueGrey,),
            Container(child: ExTextureWidget(img: window.defaultRouteName=="openGl 绘制纹理"?img:imgUrl,width: 50, height: 50,drawType: drawType,),color: Colors.blueGrey,),
            Container(child: ExTextureWidget(img: window.defaultRouteName=="openGl 绘制纹理"?img:imgUrl,width: 50, height: 50,drawType: drawType,),color: Colors.blueGrey,),
            Container(child: ExTextureWidget(img: window.defaultRouteName=="openGl 绘制纹理"?img:imgUrl,width: 50, height: 150,drawType: drawType,),color: Colors.blueGrey,),
            Container(child: ExTextureWidget(img: window.defaultRouteName=="openGl 绘制纹理"?img:imgUrl,width: 50, height: 50,drawType: drawType,),color: Colors.blueGrey,),
            Container(child: ExTextureWidget(img: window.defaultRouteName=="openGl 绘制纹理"?img:imgUrl,width: 50, height: 50,drawType: drawType,),color: Colors.blueGrey,),
            Container(child: ExTextureWidget(img: window.defaultRouteName=="openGl 绘制纹理"?img:imgUrl,width: 50, height: 50,drawType: drawType,),color: Colors.blueGrey,),
            Container(child: ExTextureWidget(img: window.defaultRouteName=="openGl 绘制纹理"?img:imgUrl,width: 50, height: 50,drawType: drawType,),color: Colors.blueGrey,),
            Container(child: ExTextureWidget(img: window.defaultRouteName=="openGl 绘制纹理"?img:imgUrl,width: 50, height: 50,drawType: drawType,),color: Colors.blueGrey,),
            Offstage(offstage:window.defaultRouteName=="openGl 绘制纹理"?true:false,child: Container(child: ExTextureWidget(img: gifImg,width: 200, height: 100,drawType: drawType,asGif: true,)),),
            Offstage(offstage:window.defaultRouteName=="openGl 绘制纹理"?true:false,child:Container(child: ExTextureWidget(img: gifImg,width: 100, height: 100,drawType: drawType,asGif: true,))),
            Container(margin:EdgeInsets.only(top: 30),child: Text("显示带有边缘锯齿的无需理会,是因为Flow绘制子Widget边缘计算的差异"),color: Colors.deepOrange,),
            Offstage(offstage:window.defaultRouteName=="openGl 绘制纹理"?false:true,child:Container(margin:EdgeInsets.only(top: 20),child: Text("OpenGl 暂不支持网络图片 如果有需要把获取Bitmap的方式修改一下即可"),color: Colors.deepOrange,)),
          ],),),
    bottomNavigationBar:BottomNavigationBar(
        items: bottomNavItems,
         currentIndex: currentIndex,
         onTap:(index){
         _changePage(index);} ),
    );
  }
  List<BottomNavigationBarItem> bottomNavItems = [
    BottomNavigationBarItem(
      icon: Icon(Icons.height),
      title: Text("显示模式切换"),
    ),
    BottomNavigationBarItem(
      icon: Icon(Icons.refresh_sharp),
      title: Text("更换图片"),
    ),
  ];

  void _changePage(int index) {
    if(window.defaultRouteName=="openGl 绘制纹理"&&index==0){
      Toast.toast(context,msg:"openGl 暂不支持模式变更",position: ToastPostion.bottom);
      return;
    }
    setState(() {
      currentIndex=index;
       gifImg=gifImg=="fff"?"eee":"fff" ;
      if(index == 1) {
        img = img == "bbb" ? "aaa" : "bbb";
        imgUrl=imgUrl=="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1606373040028&di=8ba95c56fab1093b37ea98580d9c2d83&imgtype=0&src=http%3A%2F%2Fa4.att.hudong.com%2F27%2F67%2F01300000921826141299672233506.jpg"?
        "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1945499115,3652225039&fm=15&gp=0.jpg"
        :"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1606373040028&di=8ba95c56fab1093b37ea98580d9c2d83&imgtype=0&src=http%3A%2F%2Fa4.att.hudong.com%2F27%2F67%2F01300000921826141299672233506.jpg";
      }else {
        drawType = drawType == DrawType.center ? DrawType.fitXy : DrawType.center;
      }
    });
  }
}


class TestFlowDelegate extends FlowDelegate {
  EdgeInsets margin = EdgeInsets.zero;

  TestFlowDelegate({this.margin});

  @override
  void paintChildren(FlowPaintingContext context) {
    var x = margin.left;
    var y = margin.top;
    //计算每一个子widget的位置
    for (int i = 0; i < context.childCount; i++) {
      var w = context.getChildSize(i).width + x + margin.right;
      if (w < context.size.width) {
        context.paintChild(
            i,
            transform: new Matrix4.compose(Vector.Vector3(x,y,0.0), Vector.Quaternion(0.0,0.0,0.3,0.1), Vector.Vector3(1.0,1.0,1.0))
        );
        x = w + margin.left;
      } else {
        x = margin.left;
        y += context.getChildSize(i).height + margin.top + margin.bottom;
        //绘制子widget(有优化)
        context.paintChild(i,
            transform: Matrix4.translationValues(x, y, 0.0) //位移
        );
        x += context.getChildSize(i).width + margin.left + margin.right;
      }
    }
  }

  getSize(BoxConstraints constraints) {
    //指定Flow的大小
    return Size(double.infinity, double.infinity);
  }

  @override
  bool shouldRepaint(FlowDelegate oldDelegate) {
    return oldDelegate != this;
  }
}
