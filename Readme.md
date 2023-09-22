# Backlog CLI

Backlogの課題一覧、詳細をコマンドから取得できるCLIアプリです。
対象環境: `backlog.jp`のみ


## 実行方法

ビルド
```bash
$ ./scripts/dist.sh
```

バイナリファイルの実行
```bash
$ ./dist/bin/app
```

## 機能一覧

appに続けて下記項目名を入力するとその機能が利用可能

### init

本アプリを利用できるようにAPIキー、スペース、プロジェクトキーを登録

### list

Backlogから担当者が自分に割り当たっている課題一覧を取得

`-a`オプションをつけると自分以外の課題も取得 

### show

課題の詳細情報を取得

`--id`に続けて課題IDを入力する必要がある

`-c`をつけると課題に対するコメントも取得する

## 開発環境

| Name   | Version | Description            |
| ------ | ------- | ---------------------- |
| java   | 17.0.8  |                        |
| gradle | 8.3     | パッケージマネージャー |

### コンパイル

ターミナルで下記コマンドを実行
VSCode利用時は`Ctrl + shift + B`でも可能
```bash
$ ./gradlew shadowJar
```

app/build/libs以下にjarファイルが生成される

### 実行

ターミナルで下記コマンドを実行
```bash
$ ./gradlew run
```

gradlewに引数をつける場合は
```bash
$ ./gradlew run --args='--name foo'
```

### 主要ライブラリ

- jacksone: JSON操作
- JCommander: 引数解析
- backlog4j: Backlog API client

### gradleプラグイン

- Shadow: 外部ライブラリをまとめて１つのJARファイル(FatJar)を作成

## 参考

### 環境構築

- [Gradle ことはじめ (Java プロジェクトを作成して、外部ライブラリをまとめてひとつの実行可能 JAR を生成するまで) - Qiita](https://qiita.com/niwasawa/items/425b766578133f14d516)
