# Oracle Transaction Manager for Microservices(MicroTx)ハンズオン資材

チュートリアルは[こちら](https://oracle-japan.github.io/ocitutorials/cloud-native/microtx-for-beginners/)

# 注意事項

サンプルアプリケーションのうち、Flightアプリケーションのコンテナイメージを再ビルドする際は事前に以下の2点を実施してください

* Oracle Instant Clientのダウンロードを行い、flightディレクトリ直下に配置
  * [ダウンロードはこちら](https://download.oracle.com/otn_software/linux/instantclient/instantclient-basic-linuxx64.zip)

* Node.js用のMicroTxライブラリをflightディレクトリ直下に配置
  * [チュートリアル](https://oracle-japan.github.io/ocitutorials/cloud-native/microtx-for-beginners/)でダウンロードするMicroTx資材の中に`tmmlib-node-22.3.tgz`というファイルが含まれているので、これをflightディレクトリ直下に配置
