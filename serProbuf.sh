#!/usr/bin/env bash
protoc --java_out=src/main/java/ src/main/java/com/game/protobuf/Goods.proto
protoc --java_out=src/main/java/ src/main/java/com/game/protobuf/Map.proto
protoc --java_out=src/main/java/ src/main/java/com/game/protobuf/Npc.proto
protoc --java_out=src/main/java/ src/main/java/com/game/protobuf/Role.proto
protoc --proto_path=src/main/java/com/game/protobuf/ --java_out=src/main/java/ src/main/java/com/game/protobuf/MsgAuctionInfo.proto
protoc --proto_path=src/main/java/com/game/protobuf/ --java_out=src/main/java/ src/main/java/com/game/protobuf/MsgBossInfo.proto
protoc --proto_path=src/main/java/com/game/protobuf/ --java_out=src/main/java/ src/main/java/com/game/protobuf/MsgChatInfo.proto
protoc --proto_path=src/main/java/com/game/protobuf/ --java_out=src/main/java/ src/main/java/com/game/protobuf/MsgEmailInfo.proto
protoc --proto_path=src/main/java/com/game/protobuf/ --java_out=src/main/java/ src/main/java/com/game/protobuf/MsgEquipInfo.proto
protoc --proto_path=src/main/java/com/game/protobuf/ --java_out=src/main/java/ src/main/java/com/game/protobuf/MsgGangInfo.proto
protoc --proto_path=src/main/java/com/game/protobuf/ --java_out=src/main/java/ src/main/java/com/game/protobuf/MsgGoodsInfo.proto
protoc --proto_path=src/main/java/com/game/protobuf/ --java_out=src/main/java/ src/main/java/com/game/protobuf/MsgMapInfo.proto
protoc --proto_path=src/main/java/com/game/protobuf/ --java_out=src/main/java/ src/main/java/com/game/protobuf/MsgNpcInfo.proto
protoc --proto_path=src/main/java/com/game/protobuf/ --java_out=src/main/java/ src/main/java/com/game/protobuf/MsgRankInfo.proto
protoc --proto_path=src/main/java/com/game/protobuf/ --java_out=src/main/java/ src/main/java/com/game/protobuf/MsgRoleInfo.proto
protoc --proto_path=src/main/java/com/game/protobuf/ --java_out=src/main/java/ src/main/java/com/game/protobuf/MsgShopInfo.proto
protoc --proto_path=src/main/java/com/game/protobuf/ --java_out=src/main/java/ src/main/java/com/game/protobuf/MsgSkillInfo.proto
protoc --proto_path=src/main/java/com/game/protobuf/ --java_out=src/main/java/ src/main/java/com/game/protobuf/MsgTaskInfo.proto
protoc --proto_path=src/main/java/com/game/protobuf/ --java_out=src/main/java/ src/main/java/com/game/protobuf/MsgTradeInfo.proto
protoc --proto_path=src/main/java/com/game/protobuf/ --java_out=src/main/java/ src/main/java/com/game/protobuf/MsgUserInfo.proto