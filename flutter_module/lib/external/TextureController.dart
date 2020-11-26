import 'dart:async';

import 'package:flutter/services.dart';
import 'package:flutter_module/external/ExTextureWidget.dart';

/*
 * @auther YuanShuai
 * @created at 2020/5/11 23:32
 * @desc 外接纹理桥接通道
 */
class TextureController {
  static const MethodChannel _channel =
      const MethodChannel('duia_texture_channel');
  int textureId;

  Future<int> initialize(
      String img, bool asGif, double width, double height, int drawType) async {
    textureId = await _channel.invokeMethod('create', {
      'img': img,
      'asGif': asGif,
      'width': width,
      'height': height,
      'drawType': drawType,
    });
    return textureId;
  }

  Future<Null> dispose() {
    _channel.invokeMethod('dispose', {'textureId': textureId});
    textureId = null;
  }

  bool get isInitialized => textureId != null;
}
