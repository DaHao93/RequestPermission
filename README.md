# RequestPermission
Android动态权限申请框架

#### 软件架构
基于AspectJX实现动态权限申请

#### 使用说明
```
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}

```
```
dependencies {
	        implementation 'com.github.SinoHao:RequestPermission:1.0.0'
	}
```
```
 /**
   * execWhenRejected =true 继续走checkPermission里方法
   * tipMode 权限拒绝以后提示类型
   */
 @RequestPermissions(value = {相关权限},execWhenRejected=true,tipMode = TipMode.Toast)
 private void checkPermission() {
    //动态申请权限结束逻辑
 }
```
### 自定义拒绝弹窗
自定义弹窗继承PermissionRefuseDialog
```
PermissionConfig.setRefuseDialog(PermissionRefuseDialog refuseDialog);

```
