-- user
INSERT INTO `saver`.`user` (`user_id`, `created_at`, `created_by`, `modified_at`, `modified_by`, `deleted`, `dormant`,
                            `email`,
                            `email_verified`,
                            `family_name`, `given_name`, `hashed_password`, `login_id`, `profile_image_url`,
                            `provider_type`, `role_type`, `withdrawn`)
VALUES ('1', now(), 'SYSTEM', now(), 'SYSTEM', '0', '0', 'ssjf409@gmail.com', '1', '장', '동혁',
        '$2a$10$deMiCXt/NxMxY6IpR9V9reoBU8.wn0GxcDxl3i5JVl0M8CiHjbjFm', 'ssjf409@gmail.com',
        'https://previews.123rf.com/images/aquir/aquir1311/aquir131100316/23569861-sample-grunge-red-round-stamp.jpg',
        'LOCAL', 'USER', '0');


INSERT INTO `saver`.`node` (`node_id`, `created_at`, `created_by`, `modified_at`, `modified_by`, `deleted`,
                            `content`, `description`, `favorite`, `name`, `parent_node_id`, `type`, `user_id`)
VALUES ('1', now(), 'SYSTEM', now(), 'SYSTEM', '0',
        null, null, '0', null, null, 'HEAD', '1');

INSERT INTO `saver`.`node` (`node_id`, `created_at`, `created_by`, `modified_at`, `modified_by`, `deleted`,
                            `content`, `description`, `favorite`, `name`, `parent_node_id`, `type`, `user_id`)
VALUES ('2', now() + 1, 'SYSTEM', now() + 1, 'SYSTEM', '0',
        'No.2 node content', 'No.2 node description', '0', 'No.2 name', '1', 'URL', '1');

INSERT INTO `saver`.`node` (`node_id`, `created_at`, `created_by`, `modified_at`, `modified_by`, `deleted`,
                            `content`, `description`, `favorite`, `name`, `parent_node_id`, `type`, `user_id`)
VALUES ('3', now() + 2, 'SYSTEM', now() + 2, 'SYSTEM', '0',
        'No.3 node content', 'No.3 node description', '0', 'No.3 name', '1', 'URL', '1');

INSERT INTO `saver`.`node` (`node_id`, `created_at`, `created_by`, `modified_at`, `modified_by`, `deleted`,
                            `content`, `description`, `favorite`, `name`, `parent_node_id`, `type`, `user_id`)
VALUES ('4', now() + 3, 'SYSTEM', now() + 3, 'SYSTEM', '0',
        'No.4 node content', 'No.4 node description', '0', 'No.4 name', '1', 'URL', '1');

INSERT INTO `saver`.`node` (`node_id`, `created_at`, `created_by`, `modified_at`, `modified_by`, `deleted`,
                            `content`, `description`, `favorite`, `name`, `parent_node_id`, `type`, `user_id`)
VALUES ('5', now() + 4, 'SYSTEM', now() + 5, 'SYSTEM', '0',
        'No.5 node content', 'No.5 node description', '0', 'No.5 name', '1', 'URL', '1');

INSERT INTO `saver`.`node` (`node_id`, `created_at`, `created_by`, `modified_at`, `modified_by`, `deleted`,
                            `content`, `description`, `favorite`, `name`, `parent_node_id`, `type`, `user_id`)
VALUES ('6', now() + 6, 'SYSTEM', now() + 6, 'SYSTEM', '0',
        'No.6 node content', 'No.6 node description', '0', 'No.6 name', '1', 'URL', '1');

INSERT INTO `saver`.`node` (`node_id`, `created_at`, `created_by`, `modified_at`, `modified_by`, `deleted`,
                            `content`, `description`, `favorite`, `name`, `parent_node_id`, `type`, `user_id`)
VALUES ('7', now() + 7, 'SYSTEM', now() + 7, 'SYSTEM', '0',
        'No.7 node content', 'No.7 node description', '0', 'No.7 name', '1', 'URL', '1');
