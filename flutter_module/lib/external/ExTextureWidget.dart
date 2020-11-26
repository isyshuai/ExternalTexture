import 'package:flutter/material.dart';

import 'TextureController.dart';

/*
 * @auther YuanShuai
 * @created at 2020/5/11 23:32
 * @desc 外接纹理控件
 */
class ExTextureWidget extends StatefulWidget {
  final double width;
  final double height;
  final bool asGif;
  final String img;
  final DrawType drawType;

  const ExTextureWidget(
      {Key key,
      this.img,
      this.width = 0,
      this.drawType = DrawType.fitXy,
      this.height = 0,
      this.asGif = false})
      : assert(img != null),
        super(key: key);

  @override
  State<StatefulWidget> createState() => _ExTextureWidget();
}

enum DrawType {
  fitXy,
  center,
}

class _ExTextureWidget extends State<ExTextureWidget>
    with WidgetsBindingObserver {
  final _controller = TextureController();
  var imgName1 = "";
  var drawType = DrawType.fitXy;

  @override
  void initState() {
    super.initState();
    WidgetsBinding.instance.addObserver(this);
  }

  @override
  void dispose() {
    super.dispose();
    WidgetsBinding.instance.removeObserver(this);
  }

  @override
  void didChangeAppLifecycleState(AppLifecycleState state) {
    if (state == AppLifecycleState.resumed) {
      if (!_controller.isInitialized) {
        initializeController();
      }
      print(
          'resumed -------------------------------------------------------------------------------');
    } else if (state == AppLifecycleState.paused) {
      print(
          'paused -------------------------------------------------------------------------------');
    } else if (state == AppLifecycleState.detached) {
      print(
          'detached -------------------------------------------------------------------------------');
      _controller.dispose();
    } else if (state == AppLifecycleState.inactive) {
      print(
          'inactive -------------------------------------------------------------------------------');
    }
  }

  Future<Null> initializeController() async {
    var mDrawType = 1;
    if (widget.drawType == DrawType.fitXy) {
      mDrawType = 1;
    } else if (widget.drawType == DrawType.center) {
      mDrawType = 2;
    }
    await _controller.initialize(
        widget.img, widget.asGif, widget.width, widget.height, mDrawType);
    setState(() {});
  }

  @override
  Widget build(BuildContext context) {
    if (imgName1 == "") {
      imgName1 = widget.img;
    } else if (imgName1 != widget.img) {
      _controller.dispose();
      imgName1 = widget.img;
      initializeController();
    } else if (drawType != widget.drawType) {
      _controller.dispose();
      drawType = widget.drawType;
      initializeController();
    }
    return Container(
      width:
          widget.width <= 0 ? MediaQuery.of(context).size.width : widget.width,
      height: widget.height <= 0
          ? MediaQuery.of(context).size.height
          : widget.height,
      child: _controller.isInitialized
          ? Texture(textureId: _controller.textureId)
          : null,
    );
  }
}
