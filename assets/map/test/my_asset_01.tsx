<?xml version="1.0" encoding="UTF-8"?>
<tileset version="1.10" tiledversion="1.11.0" name="my_asset_01" tilewidth="16" tileheight="16" spacing="1" tilecount="132" columns="12">
 <image source="tilemap.png" width="203" height="186"/>
 <tile id="1" probability="0.45"/>
 <tile id="2" probability="0.11"/>
 <tile id="3">
  <objectgroup draworder="index" id="2">
   <object id="2" name="a" x="6.63636" y="5.18182" width="6.09091" height="5.36364"/>
  </objectgroup>
 </tile>
 <tile id="12" probability="0.3"/>
 <tile id="13" probability="0.3"/>
 <tile id="14" probability="0.3"/>
 <tile id="24" probability="0.3"/>
 <tile id="26" probability="0.3"/>
 <tile id="27">
  <objectgroup draworder="index" id="2">
   <object id="1" name="aa" x="1" y="0" width="12" height="15"/>
  </objectgroup>
 </tile>
 <tile id="36" probability="0.3"/>
 <tile id="37" probability="0.3"/>
 <tile id="38" probability="0.3"/>
 <tile id="39" probability="0.06"/>
 <tile id="40" probability="0.06"/>
 <tile id="41" probability="0.06"/>
 <tile id="42" probability="0.06"/>
 <tile id="43" probability="0.01"/>
 <wangsets>
  <wangset name="城墙" type="mixed" tile="126">
   <wangcolor name="城墙顶角" color="#ff0000" tile="122" probability="1"/>
   <wangcolor name="城墙顶面" color="#00ff00" tile="109" probability="1"/>
   <wangcolor name="城墙面" color="#0000ff" tile="126" probability="1"/>
   <wangtile tileid="96" wangid="0,0,2,2,2,0,0,0"/>
   <wangtile tileid="97" wangid="0,0,2,2,2,2,2,0"/>
   <wangtile tileid="98" wangid="0,0,0,0,2,2,2,0"/>
   <wangtile tileid="108" wangid="2,2,2,2,2,0,0,0"/>
   <wangtile tileid="109" wangid="2,2,2,2,2,2,2,2"/>
   <wangtile tileid="110" wangid="2,0,0,0,2,2,2,2"/>
   <wangtile tileid="120" wangid="2,2,2,3,3,0,0,0"/>
   <wangtile tileid="121" wangid="2,2,2,3,3,3,2,2"/>
   <wangtile tileid="122" wangid="2,0,0,0,3,3,2,2"/>
   <wangtile tileid="126" wangid="3,3,3,3,3,3,3,3"/>
  </wangset>
  <wangset name="地面" type="mixed" tile="2">
   <wangcolor name="土" color="#ffaa00" tile="-1" probability="1"/>
   <wangcolor name="草地" color="#00ff00" tile="-1" probability="1"/>
   <wangtile tileid="0" wangid="2,2,2,2,2,2,2,2"/>
   <wangtile tileid="1" wangid="2,2,2,2,2,2,2,2"/>
   <wangtile tileid="2" wangid="2,2,2,2,2,2,2,2"/>
   <wangtile tileid="12" wangid="2,2,1,1,1,2,2,2"/>
   <wangtile tileid="13" wangid="2,2,1,1,1,1,1,2"/>
   <wangtile tileid="14" wangid="2,2,2,2,1,1,1,2"/>
   <wangtile tileid="24" wangid="1,1,1,1,1,2,2,2"/>
   <wangtile tileid="25" wangid="1,1,1,1,1,1,1,1"/>
   <wangtile tileid="26" wangid="1,2,2,2,1,1,1,1"/>
   <wangtile tileid="36" wangid="1,1,1,2,2,2,2,2"/>
   <wangtile tileid="37" wangid="1,1,1,2,2,2,1,1"/>
   <wangtile tileid="38" wangid="1,2,2,2,2,2,1,1"/>
   <wangtile tileid="39" wangid="1,1,1,1,1,1,1,1"/>
   <wangtile tileid="40" wangid="1,1,1,1,1,1,1,1"/>
   <wangtile tileid="41" wangid="1,1,1,1,1,1,1,1"/>
   <wangtile tileid="42" wangid="1,1,1,1,1,1,1,1"/>
   <wangtile tileid="43" wangid="2,2,2,2,2,2,2,2"/>
  </wangset>
 </wangsets>
</tileset>
