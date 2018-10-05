import 'package:flutter/material.dart';

class HomeComponents extends StatefulWidget {
  @override
  HomeContent createState() => new HomeContent();
}

class HomeContent extends State<HomeComponents> {
  String contentData = "";

  @override
  void initState() {
    super.initState();
    initData();
  }

  @override
  Widget build(BuildContext context) {
    return new Scaffold(
      backgroundColor: new Color.fromARGB(255, 242, 242, 245),
      appBar: new AppBar(
        elevation: 0.0,
        title: new Text('',
            style: new TextStyle(fontSize: 20.0, color: Colors.white)),
      ),
      body: new Text(
        contentData,
        style: new TextStyle(fontSize: 20.0),
      ),
    );
  }

  void initData() {
    setState(() {
      contentData = "Home页面";
    });

  }

}