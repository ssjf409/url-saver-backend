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

