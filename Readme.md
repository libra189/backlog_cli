# Java

## 実行方法

ビルド
```bash
$ ./scripts/dist.sh
```

VSCode利用時は下記方法でビルド可能
`Ctrl + shift + p`から`Run Task > dist`

バイナリファイルの実行
```bash
$ ./dist/bin/app
```

## 開発環境

| Name | Version | Description |
| --- | --- | --- |
| java | 17.0.8 | |
| gradle | 8.3 | パッケージマネージャー |

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
$ java -jar /app/build/libs/app-all.jar
```
または
```bash
$ ./gradlew run
```

gradlewに引数をつける場合は
```bash
$ ./gradlew run --args='--name foo'
```

### ライブラリ

- jackson-core: JSON操作
- JCommander: 引数解析

### gradleプラグイン

- Shadow: 外部ライブラリをまとめて１つのJARファイル(FatJar)を作成

## 参考

### 環境構築

- [Gradle ことはじめ (Java プロジェクトを作成して、外部ライブラリをまとめてひとつの実行可能 JAR を生成するまで) - Qiita](https://qiita.com/niwasawa/items/425b766578133f14d516)
