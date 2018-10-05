import 'package:flutter/material.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/foundation.dart';
import 'package:perfectapp/APP/view/IconTab.dart';
import 'package:perfectapp/APP/components/home/HomeComponents.dart';
import 'package:perfectapp/APP/components/orderInfo/OrderInfoComponents.dart';
import 'package:perfectapp/APP/components/pushInfo/PushInfoComponents.dart';
import 'package:perfectapp/APP/components/userCenter/UserCenterComponents.dart';

const double _kTabTextSize = 11.0;

void main() => runApp(new HAIWANAPP()); //运行的主入口  类似RN 中的index

class HAIWANAPP extends StatelessWidget {
  //主入口，为了好看抽离出来而已
  @override
  Widget build(BuildContext context) {
    //一般而言最后返回的是个MaterialAPP  的对象/页面
    //具体配置，此处作title  Theme主体配置  home指向要显示的页面
    return new MaterialApp(
      title: "嗨玩游戏",
      theme: new ThemeData(
        primaryIconTheme: const IconThemeData(color: Colors.white),
        brightness: Brightness.light,
        primaryColor: new Color.fromARGB(255, 0, 215, 198),
        accentColor: Colors.cyan[300],
      ),
      home: new MainContent(),
    );
  }
}

/*首页容器
 */
class MainContent extends StatefulWidget {
  @override
  HomeState createState() => new HomeState();
}

/**
 * 具体实现逻辑，此时也不太清晰，若有RN经验可理解为
 *
 * state保留状态值的对象
 *
 */
class HomeState extends State<MainContent> with SingleTickerProviderStateMixin {
  int _currentIndex = 0;
  TabController _controller;
  VoidCallback onChanged;

  @override
  void initState() {
    super.initState();
    _controller =
        new TabController(initialIndex: _currentIndex, length: 4, vsync: this);
    onChanged = () {
      setState(() {
        _currentIndex = this._controller.index;
      });
    };

    _controller.addListener(onChanged);
  }

  @override
  void dispose() {
    _controller.removeListener(onChanged);
    _controller.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return new Scaffold(
      body: new TabBarView(
        children: <Widget>[
          new HomeComponents(),
          new OrderInfoComponents(),
          new PushInfoComponents(),
          new UserCenterComponents()
        ],
        controller: _controller,
      ),
      bottomNavigationBar: new Material(
        color: Colors.white,
        child: new TabBar(
          controller: _controller,
          indicatorSize: TabBarIndicatorSize.label,
          labelStyle: new TextStyle(fontSize: _kTabTextSize),
          tabs: <IconTab>[
            new IconTab(
              icon: _currentIndex == 0
                  ? "assets/images/icon_nav_home_on.png"
                  : "assets/images/icon_nav_home.png",
              text: "首页",
            ),
            new IconTab(
              icon: _currentIndex == 1
                  ? "assets/images/icon_nav_community_on.png"
                  : "assets/images/icon_nav_community.png",
              text: "社区",
            ),
            new IconTab(
              icon: _currentIndex == 2
                  ? "assets/images/icon_nav_order_info_on.png"
                  : "assets/images/icon_nav_order_info.png",
              text: "消息",
            ),
            new IconTab(
              icon: _currentIndex == 3
                  ? "assets/images/icon_nav_user_center_on.png"
                  : "assets/images/icon_nav_user_center.png",
              text: "我的",
            ),
          ],
        ),
      ),
    );
  }
}
