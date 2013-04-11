db.appleAppInfo.ensureIndex({appId:1},{name:"uk_appleAppInfo_appid",unique:true});
db.appleAppInfo.ensureIndex({genre:1},{name:"idx_appleAppInfo_genre"});
db.appleAppInfo.ensureIndex({genreId:1},{name:"idx_appleAppInfo_genreId"});
db.appleAppInfo.ensureIndex({appName:1},{name:"idx_appleAppInfo_appName"});

db.appleAppHistoryTop.ensureIndex({appId:1,genreId:1,popId:1,createDate:1},{name:"uk_appleAppHistoryTop_appId_genreId_createDate",unique:true});

db.appleAppCurrentTop.ensureIndex({genreId:1,ranking:1,popId:1,},{name:"idx_appleAppCurrentTop_genreId_ranking",unique:true});
db.appleAppCurrentTop.ensureIndex({appId:1},{name:"uk_appleAppCurrentTop_appid"});
