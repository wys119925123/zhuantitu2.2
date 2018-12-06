create table zt_thematic_polyline_equipment (
   id                   SERIAL            not null,
   menuid	            INT4                 null,
   name                 VARCHAR(255)         null,
   floorid              VARCHAR(255)         null,
   geom                 geometry             null,
   fillcolor            VARCHAR(6)           null,
   strokewidth          INT4                 null,
   equipmenttype		INT4                 null,
   strokecolor          VARCHAR(6)           null,
   posttime				timestamp			 null,
   column1              text                 null,
   column2              text                 null,
   column3              text                 null,
   column4              text                 null,
   column5              text                 null,
   column6              text                 null,
   column7              text                 null,
   column8              text                 null,
   column9              text                 null,
   column10             text                 null,
   column11             text                 null,
   column12             text                 null,
   constraint PK_ZT_THEMATIC_POLYLINE_EQUIPM primary key (id)
);

comment on table zt_thematic_polyline_equipment is
'专题图线路设备：zt_thematic_polyline_equipment';


alter table zt_thematic_polyline_equipment
   add constraint FK_ZT_THEMA_REFERE19_ZT_THEMA foreign key (menuid)
      references zt_thematic_map_menu (menuid)
      on delete cascade on update cascade;


create table zt_thematic_equipment_image (
   imageid              SERIAL               not null,
   id                   INT4                 null,
   name                 VARCHAR(100)         null,
   path                 VARCHAR(255)         null,
   orderid              INT4                 null,
   memo                 VARCHAR(255)         null,
   constraint PK_ZT_THEMATIC_EQUIPMENT_IMAGE primary key (imageid)
);

comment on table zt_thematic_equipment_image is
'线路设备图片：zt_thematic_equipment_image';

alter table zt_thematic_equipment_image
   add constraint FK_ZT_THEMA_REFERE20_ZT_THEMA foreign key (id)
      references zt_thematic_polyline_equipment (id)
      on delete cascade on update cascade;
      
INSERT INTO "zt_table_clumn_define" ("table_name", "column_name", "categoryid", "column_cnname", "column_type", "is_required", "orderid", "is_show", "memo") VALUES ('zt_thematic_polyline_equipment', 'name', 0, '名称',            0, 1, 1, 1, NULL);
INSERT INTO "zt_table_clumn_define" ("table_name", "column_name", "categoryid", "column_cnname", "column_type", "is_required", "orderid", "is_show", "memo") VALUES ('zt_thematic_polyline_equipment', 'column1', 0, '字段1',        0, 0, 6, 0, NULL);
INSERT INTO "zt_table_clumn_define" ("table_name", "column_name", "categoryid", "column_cnname", "column_type", "is_required", "orderid", "is_show", "memo") VALUES ('zt_thematic_polyline_equipment', 'column2', 0, '字段2',        0, 0, 7, 0, NULL);
INSERT INTO "zt_table_clumn_define" ("table_name", "column_name", "categoryid", "column_cnname", "column_type", "is_required", "orderid", "is_show", "memo") VALUES ('zt_thematic_polyline_equipment', 'column3', 0, '字段3',        0, 0, 8, 0, NULL);
INSERT INTO "zt_table_clumn_define" ("table_name", "column_name", "categoryid", "column_cnname", "column_type", "is_required", "orderid", "is_show", "memo") VALUES ('zt_thematic_polyline_equipment', 'column4', 0, '字段4',        0, 0, 9, 0, NULL);
INSERT INTO "zt_table_clumn_define" ("table_name", "column_name", "categoryid", "column_cnname", "column_type", "is_required", "orderid", "is_show", "memo") VALUES ('zt_thematic_polyline_equipment', 'column5', 0, '字段5',        0, 0, 10, 0, NULL);
INSERT INTO "zt_table_clumn_define" ("table_name", "column_name", "categoryid", "column_cnname", "column_type", "is_required", "orderid", "is_show", "memo") VALUES ('zt_thematic_polyline_equipment', 'column6', 0, '字段6',        0, 0, 11, 0, NULL);
INSERT INTO "zt_table_clumn_define" ("table_name", "column_name", "categoryid", "column_cnname", "column_type", "is_required", "orderid", "is_show", "memo") VALUES ('zt_thematic_polyline_equipment', 'column7', 0, '字段7',        0, 0, 12, 0, NULL);
INSERT INTO "zt_table_clumn_define" ("table_name", "column_name", "categoryid", "column_cnname", "column_type", "is_required", "orderid", "is_show", "memo") VALUES ('zt_thematic_polyline_equipment', 'column8', 0, '字段8',        0, 0, 13, 0, NULL);
INSERT INTO "zt_table_clumn_define" ("table_name", "column_name", "categoryid", "column_cnname", "column_type", "is_required", "orderid", "is_show", "memo") VALUES ('zt_thematic_polyline_equipment', 'column9', 0, '字段9',        0, 0, 14, 0, NULL);
INSERT INTO "zt_table_clumn_define" ("table_name", "column_name", "categoryid", "column_cnname", "column_type", "is_required", "orderid", "is_show", "memo") VALUES ('zt_thematic_polyline_equipment', 'column10', 0, '字段10',      0, 0, 15, 0, NULL);
INSERT INTO "zt_table_clumn_define" ("table_name", "column_name", "categoryid", "column_cnname", "column_type", "is_required", "orderid", "is_show", "memo") VALUES ('zt_thematic_polyline_equipment', 'column11', 0, '字段11',      0, 0, 16, 0, NULL);
INSERT INTO "zt_table_clumn_define" ("table_name", "column_name", "categoryid", "column_cnname", "column_type", "is_required", "orderid", "is_show", "memo") VALUES ('zt_thematic_polyline_equipment', 'column12', 0, '字段12',      0, 0, 17, 0, NULL);



INSERT INTO "zt_table_clumn_define" ("table_name", "column_name", "categoryid", "column_cnname", "column_type", "is_required", "orderid", "is_show", "memo") VALUES ('zt_thematic_polyline_equipment', 'name', -1, '名称',            0, 1, 1, 1, NULL);
INSERT INTO "zt_table_clumn_define" ("table_name", "column_name", "categoryid", "column_cnname", "column_type", "is_required", "orderid", "is_show", "memo") VALUES ('zt_thematic_polyline_equipment', 'column1', -1, '字段1',        0, 0, 6, 0, NULL);
INSERT INTO "zt_table_clumn_define" ("table_name", "column_name", "categoryid", "column_cnname", "column_type", "is_required", "orderid", "is_show", "memo") VALUES ('zt_thematic_polyline_equipment', 'column2', -1, '字段2',        0, 0, 7, 0, NULL);
INSERT INTO "zt_table_clumn_define" ("table_name", "column_name", "categoryid", "column_cnname", "column_type", "is_required", "orderid", "is_show", "memo") VALUES ('zt_thematic_polyline_equipment', 'column3', -1, '字段3',        0, 0, 8, 0, NULL);
INSERT INTO "zt_table_clumn_define" ("table_name", "column_name", "categoryid", "column_cnname", "column_type", "is_required", "orderid", "is_show", "memo") VALUES ('zt_thematic_polyline_equipment', 'column4', -1, '字段4',        0, 0, 9, 0, NULL);
INSERT INTO "zt_table_clumn_define" ("table_name", "column_name", "categoryid", "column_cnname", "column_type", "is_required", "orderid", "is_show", "memo") VALUES ('zt_thematic_polyline_equipment', 'column5', -1, '字段5',        0, 0, 10, 0, NULL);
INSERT INTO "zt_table_clumn_define" ("table_name", "column_name", "categoryid", "column_cnname", "column_type", "is_required", "orderid", "is_show", "memo") VALUES ('zt_thematic_polyline_equipment', 'column6', -1, '字段6',        0, 0, 11, 0, NULL);
INSERT INTO "zt_table_clumn_define" ("table_name", "column_name", "categoryid", "column_cnname", "column_type", "is_required", "orderid", "is_show", "memo") VALUES ('zt_thematic_polyline_equipment', 'column7', -1, '字段7',        0, 0, 12, 0, NULL);
INSERT INTO "zt_table_clumn_define" ("table_name", "column_name", "categoryid", "column_cnname", "column_type", "is_required", "orderid", "is_show", "memo") VALUES ('zt_thematic_polyline_equipment', 'column8', -1, '字段8',        0, 0, 13, 0, NULL);
INSERT INTO "zt_table_clumn_define" ("table_name", "column_name", "categoryid", "column_cnname", "column_type", "is_required", "orderid", "is_show", "memo") VALUES ('zt_thematic_polyline_equipment', 'column9', -1, '字段9',        0, 0, 14, 0, NULL);
INSERT INTO "zt_table_clumn_define" ("table_name", "column_name", "categoryid", "column_cnname", "column_type", "is_required", "orderid", "is_show", "memo") VALUES ('zt_thematic_polyline_equipment', 'column10', -1, '字段10',      0, 0, 15, 0, NULL);
INSERT INTO "zt_table_clumn_define" ("table_name", "column_name", "categoryid", "column_cnname", "column_type", "is_required", "orderid", "is_show", "memo") VALUES ('zt_thematic_polyline_equipment', 'column11', -1, '字段11',      0, 0, 16, 0, NULL);
INSERT INTO "zt_table_clumn_define" ("table_name", "column_name", "categoryid", "column_cnname", "column_type", "is_required", "orderid", "is_show", "memo") VALUES ('zt_thematic_polyline_equipment', 'column12', -1, '字段12',      0, 0, 17, 0, NULL);
    