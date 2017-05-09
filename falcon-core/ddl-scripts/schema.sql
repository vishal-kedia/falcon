create database falcon;

use falcon;

CREATE TABLE `contractors` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `address_line1` varchar(128),
  `address_line2` varchar(128),
  `city` varchar(64),
  `pin_code` varchar(64),
  `phone_no` varchar(64),
  `attributes` varchar(1000) DEFAULT '{}',
  `deleted` bit(1) DEFAULT NULL,
  `created_by` varchar(64),
  `updated_by` varchar(64),
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_contractors` (`name`)
);

CREATE TABLE `projects` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `lat` double,
  `lng` double,
  `description` varchar(128),
  `project_type` varchar(64),
  `contractor_id` int(10) unsigned NOT NULL,
  `attributes` varchar(1000) DEFAULT '{}',
  `deleted` bit(1) DEFAULT NULL,
  `created_by` varchar(64),
  `updated_by` varchar(64),
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_project` (`name`,`contractor_id`)
);

CREATE TABLE `project_users` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_name` varchar(64) NOT NULL,
  `password` varchar(64),
  `name` varchar(64) NOT NULL,
  `address_line1` varchar(128),
  `address_line2` varchar(128),
  `city` varchar(64),
  `pin_code` varchar(64),
  `phone_no` varchar(64),
  `user_type` varchar(64),
  `project_id` int(10) unsigned NOT NULL,
  `attributes` varchar(1000) DEFAULT '{}',
  `deleted` bit(1) DEFAULT NULL,
  `created_by` varchar(64),
  `updated_by` varchar(64),
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_project_users` (`user_name`)
);

CREATE TABLE `user_session` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `uuid` varchar(64) NOT NULL,
  `user_id` int(10) unsigned NOT NULL,
  `attributes` varchar(1000) DEFAULT '{}',
  `deleted` bit(1) DEFAULT NULL,
  `created_by` varchar(64),
  `updated_by` varchar(64),
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_user_session` (`uuid`,`user_id`)
);

CREATE TABLE `project_vendors` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `address_line1` varchar(128),
  `address_line2` varchar(128),
  `city` varchar(64),
  `pin_code` varchar(64),
  `phone_no` varchar(64),
  `project_id` int(10) unsigned NOT NULL,
  `attributes` varchar(1000) DEFAULT '{}',
  `deleted` bit(1) DEFAULT NULL,
  `created_by` varchar(64),
  `updated_by` varchar(64),
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_project_vendors` (`name`,`project_id`)
);

CREATE TABLE `vehicle_inventory_ledger` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `vehicle_no` varchar(64) NOT NULL,
  `description` varchar(128),
  `vendor_id` int(10) unsigned NOT NULL,
  `project_id` int(10) unsigned NOT NULL,
  `attributes` varchar(1000) DEFAULT '{}',
  `deleted` bit(1) DEFAULT NULL,
  `created_by` varchar(64),
  `updated_by` varchar(64),
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_vehicle_inventory_ledger` (`vehicle_no`,`vendor_id`)
);

CREATE TABLE `material_inventory_ledger` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `material_type` varchar(64) NOT NULL,
  `inventory_type` varchar(64) NOT NULL,
  `quantity` double NOT NULL,
  `unit` varchar(64) NOT NULL,
  `supplier_id` int(10) unsigned,
  `transport_vehicle_id` int(10) unsigned,
  `reciever_vehicle_id` int(10) unsigned,
  `transaction_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `remark` varchar(128),
  `project_id` int(10) unsigned NOT NULL,
  `attributes` varchar(1000) DEFAULT '{}',
  `deleted` bit(1),
  `created_by` varchar(64),
  `updated_by` varchar(64),
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_material_inventory_ledger` (`supplier_id`,`transport_vehicle_id`,`transaction_time`)
);

CREATE TABLE `concrete_dispatch_ledger` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `concrete_type` varchar(64) NOT NULL,
  `quantity` double,
  `unit` varchar(64),
  `transport_vehicle_id` int(10) unsigned NOT NULL,
  `dispatch_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `remark` varchar(128),
  `attributes` varchar(1000) DEFAULT '{}',
  `deleted` bit(1) DEFAULT NULL,
  `created_by` varchar(64),
  `updated_by` varchar(64),
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_concrete_dispatch_ledger` (`transport_vehicle_id`,`dispatch_time`)
);