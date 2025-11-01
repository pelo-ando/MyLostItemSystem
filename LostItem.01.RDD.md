# 忘れ物管理システム要件定義書

---

## 忘れ物管理について
不特定多数の人が利用する施設、企業において、顧客の忘れ物の保管管理は必須のものである。
管理方法は、紙ベース、エクセル等の表計算ソフトを利用したものなどがある。
近年では、忘れ物の写真をタブレット端末にて撮影し、写真を含めデータベースへ登録し顧客の問合せ等に対応している企業も現存する。

## システム導入の背景と目的
顧客からの問合せに対し、いずれの管理にせよ、最終的には現品を保管場所より捜索しなければならない。
紙ベース、エクセル等による台帳方式では、顧客の忘れ物に対する説明と台帳記載の内容に相違があった場合、現品の絞り込みに労力を要する場合が発生している。

### 紙ベース、表計算ソフトなどの台帳記載方式の業務フロー
```mermaid
---
title: 紙ベース、表計算ソフトなどの台帳記載方式
---

flowchart

subgraph c4[" "]
  問合せ対応者 --> 処置担当者
  subgraph 処置担当者
    direction TB
    brcact{問合せ<br>結果別に<br>処置}
    action
    chgsts
  end
  check --照合あっても--> asign
  subgraph 問合せ対応者
    direction TB
    check{**台帳**と<br>問合せ内容<br>照合} --無くても--> asign([捜索])
  end
  登録者 --> 問合せ対応者

  subgraph 登録者
    direction TB
    regi([**台帳**へ定められた<br>情報を記載・入力]) --> save([保管])
  end
  発見者 --> 登録者
  subgraph 発見者
  direction TB
    find(忘れ物発生) --- memo([memo用紙へ情報記載])
  end
end

brcact --- action[<ul><li>郵送処置</li><li>廃棄</li></ul>] --- chgsts[台帳のStatus変更]


classDef BlackWhite fill:#fff,color:#000
class find,memo,ope,regi,save,inq,check,asign,brcact,action,chgsts BlackWhite

classDef BAqua fill:#22eeff,color:#000
class 登録者,発見者,問合せ対応者,処置担当者 BAqua

```

### 新システム方式の業務フロー
```mermaid
---
title: Webでのデータベース一元管理方式
---

flowchart

subgraph c4[" "]
  問合せ対応者 --> 処置担当者
  subgraph 処置担当者
    direction TB
    brcact{問合せ<br>結果別に<br>処置}
    action
    chgsts
  end
  check --照合あり--> asign
  subgraph 問合せ対応者
    direction TB
    check{Webデータ<br>画像を含めて<br>問合せ内容<br>照合} --照合なし-->
    nona[[探さないよ!]]
    asign([捜索])
  end
  登録者 --> 問合せ対応者

  subgraph 登録者
    direction TB
    regi([忘れ物の写真を撮りつつ<br>Webへ<br>情報を登録]) --> save([保管])
  end
  発見者 --> 登録者
  subgraph 発見者
  direction TB
    find(忘れ物発生) --- memo([memo用紙へ情報記載])
  end
end

brcact --- action[<ul><li>郵送処置</li><li>廃棄</li></ul>] --- chgsts[WebデータのStatus変更]


classDef BlackWhite fill:#fff,color:#000
class find,memo,ope,regi,save,inq,check,asign,brcact,action,chgsts BlackWhite

classDef BAqua fill:#22eeff,color:#000
class 登録者,発見者,問合せ対応者,処置担当者 BAqua

style regi color:#f00
style nona color:#f00
style chgsts color:#f00
```

## システムの概要
本システムは、忘れ物の管理システムである。忘れ物の管理(登録、検索、編集、削除
)はSpring Bootで構築されるWebシステムで行う,管理者権限を持ったものが、本システムを管理する。一般社員は忘れ物情報(現物の写真データを含めた)をサーバ上のデータベースにアクセスが可能(一部制限あり)これらの忘れ物データを一元管理することにより、忘れ物の検索などが簡便化され、かつ情報の共有化を図ることが可能となる。

```mermaid
flowchart LR

DB[(DB<br>MySQL)]
sys --- DB
sys[<br>管理システム<br>「SpringBoot」<br><br>]

user <--登録画面から<br>登録,検索,編集,削除--> sys
admin <--忘れ物情報の管理<br>および<br>一般社員と同じ<br>登録業務可能 --> sys
subgraph users
  direction TB
  user
  admin
end

login[login認証<br>「SpringBoot」]

login --> user[一般社員の場合]
login --> admin[管理者の場合]

classDef BlackWhite fill:#fff,color:#000
class DB,sys,user,admin,login,users BlackWhite

```

## 機能要件
### 管理システム機能

|機能ID|機能名|機能概要|
|---|---|---|
|LI-A-1 |ユーザー認証 |ログイン、ログアウト等の認証に関する機能|
|LI-A-2 |忘れ物登録管理 |忘れ物情報の表示、登録、編集の機能 |
|LI-B-1 |忘れ物情報管理 |発見場所、保管場所の登録機能 *1.3|
|LI-B-2 | 忘れ物データの更新情報|忘れ物データの更新情報を閲覧、削除する機能*1.1*1.3 |
|LI-B-3 |ユーザー設定 |一般社員、管理者の認証情報を追加、修正するための機能*1.3 |

### 改訂履歴
<style>
  th: {color:"#f00"}
</style>
<table>
  <th>版</th><th>日付</th><th>改訂者</th><th>内容</th>
  <tr>
    <td>1.0</td>
    <td>2025/9/19</td>
    <td>安藤敦規</td>
    <td>初版作成</td>
  </tr>
  <tr>
    <td>1.1</td>
    <td>2025/09/30<br></td>
    <td>〃</td>
    <td>LI-B-2 機能見直し修正</td>
  </tr>
  <tr>
    <td>1.3</td>
    <td>2025/11/01<br></td>
    <td>〃</td>
    <td>機能概要修正</td>
  </tr>
  <tr>
    <td>&emsp;</td>
    <td></td>
    <td></td>
    <td></td>
  </tr>
</table>