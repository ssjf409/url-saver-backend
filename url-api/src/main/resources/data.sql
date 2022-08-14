-- user
INSERT INTO `saver`.`user` (`user_id`, `created_at`, `created_by`, `modified_at`, `modified_by`, `deleted`, `dormant`,
                            `email`,
                            `email_verified`,
                            `family_name`, `given_name`, `hashed_password`, `login_id`, `profile_image_url`,
                            `provider_type`, `role_type`, `withdrawn`)
VALUES ('1', now(), 'SYSTEM', now(), 'SYSTEM', '0', '0', 'ssjf409@naver.com', '1', '장', '동혁',
        '$2a$10$FjxjsZbI2B8ajcrOrJ6cTua8IuT2JJl14ouVvMevdIwGFbaw65Iby', 'ssjf409@naver.com',
        'https://previews.123rf.com/images/aquir/aquir1311/aquir131100316/23569861-sample-grunge-red-round-stamp.jpg',
        'LOCAL', 'USER', '0');

