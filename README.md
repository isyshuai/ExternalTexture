# External_Texture
**基于canvas 与 openGl 构建Android与Flutter共享纹理 支持网络图片，本地图片，GIF图片  :tw-1f60e:**
```OpenGl 目前不支持GIF和居中 canvas相对成熟些``` :tw-1f608:
# 开发环境
```Flutter SDK : <1.22.3>```

#Android部分
**模块为android_extexture 包含 openGl 与 canvas 两种绘制方式**  :tw-1f6b5:
>注 : 两种加载无显著区别，在 demo 中 canvas 上屏会比openGl 快是因为canvas是通过glide进行缓存策略的原因

#Flutter部分
**模块为flutter_module 共提供居中显示，和拉伸显示两种显示模式，GIF图，网络图，本地资源显示方式** :tw-1f383:

#效果
