create table if not exists admin_menu
(
	seq_no bigint auto_increment
		primary key,
	menu_name varchar(128) not null,
	parent_seq_no bigint not null,
	ord bigint not null,
	reg_datetime datetime default (CURRENT_TIMESTAMP) not null,
	url varchar(1024) null,
	constraint admin_menu_name_UNIQUE
		unique (menu_name)
);

create table if not exists agent
(
	seq_no bigint auto_increment
		primary key,
	code varchar(32) not null,
	name varchar(64) not null,
	contract_start char(8) null,
	contract_end char(8) null,
	status varchar(32) default 'pending' not null,
	phone varchar(64) null,
	fax varchar(64) null,
	zip_code varchar(8) null,
	base_addr varchar(512) null,
	detail_addr varchar(512) null,
	homepage varchar(64) null,
	charge_name varchar(64) null,
	charge_org varchar(64) null,
	charge_id varchar(64) null,
	charge_pwd varchar(64) null,
	charge_email varchar(128) null,
	charge_mobile varchar(64) null,
	charge_phone varchar(64) null,
	charge_prop varchar(2048) null,
	reg_datetime datetime default (CURRENT_TIMESTAMP) not null
);

create table if not exists app
(
	app_key varchar(64) not null,
	status varchar(32) default 'active' not null,
	platform varchar(32) default 'aos' not null,
	client_prop varchar(8192) null,
	server_prop varchar(8192) null,
	mod_datetime datetime null,
	modifier_seq_no bigint null,
	app_type varchar(32) default 'user' not null,
	primary key (app_key, platform)
)
charset=utf8;

create table if not exists appversion
(
	app_key varchar(64) not null,
	version varchar(32) not null,
	version_prop varchar(8192) null,
	mod_datetime datetime null,
	modifier_seq_no bigint null,
	primary key (app_key, version),
	constraint fk_app_version
		foreign key (app_key) references app (app_key)
)
charset=utf8;

create index fk_app_version_idx
	on appversion (app_key);

create table if not exists ars_request
(
	seq_no bigint auto_increment
		primary key,
	ars_prop varchar(4096) null,
	reg_datetime datetime default (CURRENT_TIMESTAMP) not null,
	result varchar(1024) null
);

create table if not exists attachment
(
	seq_no bigint auto_increment
		primary key,
	ref_count int default 1 not null,
	origin_name varchar(128) not null,
	file_path varchar(256) not null,
	file_name varchar(128) not null,
	extension varchar(8) null,
	file_size bigint not null,
	url varchar(512) null,
	attachment_prop mediumtext null,
	deleted enum('Y', 'N') default 'N' not null,
	target_type varchar(32) default 'memberProfile' not null,
	id char(36) null,
	constraint id
		unique (id)
);

create table if not exists banner
(
	seq_no bigint auto_increment comment '배너순번'
		primary key,
	type varchar(32) not null,
	platform varchar(32) not null,
	display enum('Y', 'N') default 'Y' not null,
	name varchar(128) not null,
	start_datetime datetime not null,
	end_datetime datetime not null,
	move_type1 varchar(32) not null,
	move_type2 varchar(32) null,
	move_target varchar(512) null,
	sort_num int default 1 not null,
	click_count bigint default 0 not null,
	view_count bigint default 0 not null,
	reg_datetime datetime default (CURRENT_TIMESTAMP) not null,
	registrant_seq_no bigint not null,
	mod_datetime decimal null,
	modifier_seq_no datetime null
);

create table if not exists beta_post
(
	seq_no bigint not null comment 'beta post 순번'
		primary key,
	review varchar(255) not null comment 'beta 당첨후기',
	content varchar(255) not null comment 'beta 포스트 내용',
	code varchar(8) not null comment 'beta 경품 카테고리 코드',
	img_seq_no bigint not null comment 'beta 포스트 이미지',
	active tinyint(1) default 1 not null comment 'beta 유효성 상태'
);

create table if not exists board
(
	seq_no bigint auto_increment
		primary key,
	board_type varchar(20) default 'page_pr' not null,
	board_name varchar(64) null,
	list_format varchar(8192) null,
	view_format varchar(8192) null,
	board_prop varchar(4096) null,
	reg_datetime datetime default (CURRENT_TIMESTAMP) not null
)
charset=utf8;

create table if not exists cash_exchange
(
	seq_no bigint auto_increment comment '환전 순번'
		primary key,
	member_seq_no bigint not null comment '사용자 순번',
	bank_transfer_seq_no bigint null comment '계좌 이체 정보 순번',
	bol bigint not null comment '환전 볼',
	cash bigint not null comment '환전 요청된 현금',
	refund_cash bigint not null comment '실제 환급된 현금(제세 공과 22%제외)',
	status int default 0 not null comment '환전 상태( 0 : 환전 요청, 1 : 환전 완료)',
	bank_name varchar(64) not null comment '입금기관명',
	bank_account_id varchar(128) not null comment '입금계좌번호',
	bank_account_holder_name varchar(32) not null comment '수취인 성명',
	reg_datetime datetime not null comment '등록시각',
	mod_datetime datetime not null comment '변경시각'
);

create index member_seq_no
	on cash_exchange (member_seq_no);

create table if not exists category
(
	seq_no bigint auto_increment
		primary key,
	icon_seq_no bigint null,
	code varchar(32) null,
	category_type varchar(32) default 'store' not null,
	depth tinyint default 1 not null,
	name varchar(128) not null,
	sort_num int not null,
	status varchar(32) default 'active' not null,
	parent_seq_no bigint null,
	reg_datetime datetime default (CURRENT_TIMESTAMP) not null,
	registrant_seq_no bigint not null,
	mod_datetime datetime null,
	modifier_seq_no bigint null,
	uuid varchar(64) null comment '페이지 카테고리 아이콘 이미지 ID',
	thema tinyint(1) default 0 not null comment '테마 카테고리 여부'
)
charset=utf8;

create index thema
	on category (thema);

create table if not exists category2
(
	seq_no bigint auto_increment
		primary key,
	icon_seq_no bigint null,
	code varchar(32) null,
	category_type varchar(32) default 'store' not null,
	depth tinyint default 1 not null,
	name varchar(128) not null,
	sort_num int not null,
	status varchar(32) default 'active' not null,
	parent_seq_no bigint null,
	reg_datetime datetime default (CURRENT_TIMESTAMP) not null,
	registrant_seq_no bigint not null,
	mod_datetime datetime null,
	modifier_seq_no bigint null
);

create table if not exists coop_group
(
	seq_no bigint auto_increment
		primary key,
	name varchar(64) not null,
	priority int default 1 not null,
	status varchar(32) default 'active' not null,
	reg_datetime datetime default (CURRENT_TIMESTAMP) not null,
	icon_seq_no bigint null,
	constraint coop_group_ibfk_1
		foreign key (icon_seq_no) references attachment (seq_no)
);

create index R_tachmen_op__59e
	on coop_group (icon_seq_no);

create table if not exists cooperation
(
	seq_no bigint auto_increment
		primary key,
	name varchar(64) not null,
	commerce_type varchar(32) default 'page' not null,
	coop_prop varchar(8192) null,
	reg_datetime datetime default (CURRENT_TIMESTAMP) not null,
	page_seq_no bigint null
);

create table if not exists coop_inc_group
(
	group_seq_no bigint not null,
	coop_seq_no bigint not null,
	primary key (group_seq_no, coop_seq_no),
	constraint coop_inc_group_ibfk_1
		foreign key (group_seq_no) references coop_group (seq_no),
	constraint coop_inc_group_ibfk_2
		foreign key (coop_seq_no) references cooperation (seq_no)
);

create index R_operati_op__ba2
	on coop_inc_group (coop_seq_no);

create table if not exists country
(
	seq_no bigint auto_increment
		primary key,
	country_number varchar(8) not null,
	country_code varchar(8) not null,
	name varchar(128) not null,
	eng_name varchar(128) not null,
	currency varchar(16) not null,
	status varchar(32) default 'active' not null,
	note varchar(8192) null,
	reg_datetime datetime default (CURRENT_TIMESTAMP) not null,
	registrant_seq_no bigint not null,
	mod_datetime datetime null,
	modifier_seq_no bigint null,
	profit_tax_rate float default 22 not null comment '제세공과금 비율',
	vat float default 10 not null comment '부가세 비율',
	constraint country_code_uniq
		unique (country_code),
	constraint country_number_uniq
		unique (country_number)
)
charset=utf8;

create table if not exists country_config
(
	country_seq_no bigint not null
		primary key,
	config text null,
	constraint country_config_ibfk_1
		foreign key (country_seq_no) references country (seq_no)
)
charset=utf8;

create table if not exists coupon_template
(
	seq_no bigint auto_increment
		primary key,
	name varchar(256) not null,
	note varchar(4096) null,
	type varchar(32) default 'etc' not null,
	start_datetime datetime not null,
	end_datetime datetime not null,
	download_limit int default 1 not null,
	page_display enum('Y', 'N') default 'Y' not null,
	discount_type varchar(32) default 'amount' not null,
	discount bigint not null,
	cond varchar(4096) null,
	template_prop varchar(8192) null,
	status varchar(32) default 'active' not null,
	download_count int default 0 not null,
	gift_count int default 0 not null,
	use_count int default 0 not null,
	icon_seq_no bigint null,
	reg_datetime datetime default (CURRENT_TIMESTAMP) not null,
	publisher_seq_no bigint not null,
	publisher_type varchar(32) default 'page' not null,
	give_plus enum('Y', 'N') default 'N' not null,
	constraint fk_template_icon
		foreign key (icon_seq_no) references attachment (seq_no)
)
charset=utf8;

create table if not exists coop_coupon_template
(
	coop_seq_no bigint not null,
	template_seq_no bigint not null,
	allocate_prop varchar(4096) null,
	primary key (coop_seq_no, template_seq_no),
	constraint fk_coop_coupon_template1
		foreign key (coop_seq_no) references cooperation (seq_no),
	constraint fk_coop_coupon_template2
		foreign key (template_seq_no) references coupon_template (seq_no)
);

create index fk_coop_coupon_template1_idx
	on coop_coupon_template (coop_seq_no);

create index fk_coop_coupon_template2_idx
	on coop_coupon_template (template_seq_no);

create index fk_template_icon_idx
	on coupon_template (icon_seq_no);

create table if not exists delivery_company
(
	seq_no bigint auto_increment comment '배달주문 회사 고유 순번'
		primary key,
	company varchar(64) not null comment '배달대행 회사명 : 배달의민족, 요기요, 배달통',
	constraint company
		unique (company)
);

create table if not exists eventgroup
(
	seq_no bigint auto_increment
		primary key,
	title varchar(64) not null
);

create table if not exists faq_group
(
	seq_no bigint auto_increment
		primary key,
	name varchar(128) null,
	status varchar(32) default 'active' not null comment '자릿수 상태 - active:활성화, deactive:비활성화',
	sort_num int default 1 not null,
	reg_datetime datetime default (CURRENT_TIMESTAMP) not null,
	registrant_seq_no bigint not null,
	mod_datetime datetime null,
	modifier_seq_no bigint null
)
charset=utf8;

create table if not exists app_faq_group
(
	app_key varchar(64) not null,
	group_seq_no bigint not null,
	primary key (app_key, group_seq_no),
	constraint app_faq_group_ibfk_1
		foreign key (app_key) references app (app_key),
	constraint app_faq_group_ibfk_2
		foreign key (group_seq_no) references faq_group (seq_no)
)
charset=utf8;

create index fk_app_faq_group_2
	on app_faq_group (group_seq_no);

create table if not exists faq
(
	seq_no bigint auto_increment
		primary key,
	faq_group_seq_no bigint not null,
	status varchar(32) default 'active' not null comment '자릿수 상태 - active:활성화, deactive:비활성화',
	subject varchar(256) not null,
	contents longtext not null,
	path varchar(512) null comment '프로모션 경로',
	reg_datetime datetime default (CURRENT_TIMESTAMP) not null,
	registrant_seq_no bigint not null,
	mod_datetime datetime null,
	modifier_seq_no bigint null,
	sort_num int default 1 not null,
	platform varchar(8) default 'ios' not null comment 'device platform type : ios, aos, web, pcweb, cs',
	constraint fk_faq_faq_group1
		foreign key (faq_group_seq_no) references faq_group (seq_no)
);

create index fk_faq_faq_group1_idx
	on faq (faq_group_seq_no);

create table if not exists goods
(
	seq_no bigint auto_increment comment '상품 순번'
		primary key,
	page_seq_no bigint not null comment '상품 상점 페이지 순번',
	category_seq_no bigint not null comment '상품 카테고리 순번',
	name varchar(128) not null comment '상품명',
	hashtag varchar(256) null comment '해쉬태그',
	description varchar(1024) not null comment '상품 설명',
	count bigint default -1 not null comment '상품 수량,  -1 : 수량 제한 없음',
	status int default 1 not null comment '상품상태  1:판매중(sail), 0:완판(soldOut), -1:판매종료(expire), -2: 판매중지(stop)',
	goods_prop varchar(4096) null comment '상품 옵션',
	reg_datetime datetime not null comment '등록시각',
	mod_datetime datetime not null comment '변경시각',
	price float not null comment '상품 가격',
	expire_datetime datetime null,
	attachments varchar(1024) null comment '상품 이미지 리스트',
	sold_count bigint default 0 not null comment '상품 누적 판매 수량',
	lang varchar(8) default 'ko' not null comment '언어',
	type int default 0 not null comment '0 : 메뉴 오더 상품, 1 : 일반 구매 상품',
	reward_luckybol int default 0 not null comment '상품에 걸린 리워드 럭키볼 수',
	origin_price float not null comment '상품 원 가격',
	expire_day int null comment '구 매 후 사용처리 유효 기간',
	is_hotdeal tinyint(1) default 0 not null comment '핫딜 상품 여부',
	is_plus tinyint(1) default 0 not null comment '플러스 상품 여부',
	start_time time null comment '상품 구매 시작 시간',
	end_time time null comment '상품 구매 종료 시간',
	reward_pr_link int default 0 null comment 'PRLink 를 통해서 구매 시 리워드 럭키볼 수',
	news_datetime datetime null comment '최신 소식으로 등록 시각',
	reward_pr_review_link int default 0 null comment '구매 리뷰 광고를 통해서 구매 시 리워드 럭키볼 수'
);

create index category_seq_no
	on goods (category_seq_no);

create index end_time
	on goods (end_time);

create index expire_datetime
	on goods (expire_datetime);

create index expire_day
	on goods (expire_day);

create index is_hotdeal
	on goods (is_hotdeal);

create index is_plus
	on goods (is_plus);

create index lang
	on goods (lang);

create index news_datetime
	on goods (news_datetime);

create index page_seq_no
	on goods (page_seq_no);

create index price
	on goods (price);

create index start_time
	on goods (start_time);

create table if not exists goods_category
(
	seq_no bigint auto_increment comment '상품 카테고리 순번'
		primary key,
	parent_seq_no bigint null comment '상위카테고리 순번',
	depth tinyint not null comment '상품 카테고리 depth',
	sort_num int not null comment '정렬 순서',
	name varchar(128) not null comment '상품 카테고리 명',
	reg_datetime datetime not null comment '등록시각',
	mod_datetime datetime not null comment '변경시각',
	lang varchar(8) default 'ko' not null comment '언어',
	constraint uk_parentSeqNo_depth_name
		unique (parent_seq_no, depth, name)
);

create index depth
	on goods_category (depth, sort_num);

create index lang
	on goods_category (lang);

create index parent_seq_no
	on goods_category (parent_seq_no);

create table if not exists goods_image
(
	seq_no bigint auto_increment comment '상품 이미지 순번'
		primary key,
	goods_seq_no bigint not null comment '상품 순번',
	attach_seq_no bigint not null comment '이미지 첨부 파일 순번',
	priority int default 1 not null comment '이미지 우선 순위',
	purpose varchar(32) default 'goods' not null comment '이미지 사용 목적',
	page_seq_no bigint not null comment '상품 상점 페이지 순번',
	constraint goods_image_ibfk_1
		foreign key (goods_seq_no) references goods (seq_no)
			on delete cascade
);

create index attach_seq_no
	on goods_image (attach_seq_no);

create index goods_seq_no
	on goods_image (goods_seq_no);

create index page_seq_no
	on goods_image (page_seq_no);

create table if not exists hibernate_sequence
(
	next_val bigint null
);

create table if not exists lotto
(
	seq_no bigint default 1 not null comment '순번'
		primary key,
	lotto_times int not null comment '현제 진행중인 회차',
	lotto_prev_times bigint not null comment '최근 지난 회차',
	win_code varchar(64) null comment '최근 지난 회차 당첨 번호',
	join_luckybol bigint default 0 not null comment '참여 럭키볼 포인트, 0: 로또 티켓으로 응모, > 0: 럭키볼 포인트로 응모',
	lotto_luckybol bigint not null comment '당첨 럭키볼 금액',
	url1 varchar(1024) null comment 'lotto 당첨번호 사이트1',
	selector1 varchar(255) null comment 'lotto 당첨번호 태그1',
	success1 tinyint(1) default 1 not null comment 'lotto 당첨번호 사이트1 확인 결과',
	url2 varchar(1024) null comment 'lotto 당첨번호 사이트2',
	selector2 varchar(255) null comment 'lotto 당첨번호 태그2',
	success2 tinyint(1) default 1 not null comment 'lotto 당첨번호 사이트2 확인 결과',
	url3 varchar(1024) null comment 'lotto 당첨번호 사이트3',
	selector3 varchar(255) null comment 'lotto 당첨번호 태그3',
	success3 tinyint(1) default 1 not null comment 'lotto 당첨번호 사이트3 확인 결과',
	banner_seq_no bigint null comment 'lotto 배너 이미지 순번',
	banner_id char(36) null comment 'lotto 배너 이미지 ID',
	join_ticket_num bigint default 0 not null comment '회원가입시 로또 티켓 적립 수',
	recommend_ticket_num bigint default 0 not null comment '추천회원 가입시 로또 티켓 적립 수',
	recommendee_ticket_num bigint default 0 not null comment '추천으로 가입시 로또 티켓 적립 수',
	activate_recommend_ticket_num bigint default 0 not null comment '추천시 로또 티켓 적립 수',
	mod_datetime datetime not null comment '수정 시각',
	lotto_5_luckybol bigint default 500000 not null comment '5개 번호 당첨 럭키볼 금액',
	lotto_4_luckybol bigint default 10000 not null comment '4개 번호 당첨 럭키볼 금액',
	lotto_3_luckybol bigint default 3000 not null comment '3개 번호 당첨 럭키볼 금액',
	lotto_gift_type varchar(32) default '현금' not null comment '로또 당첨금 타입',
	lotto_5_gift_type varchar(32) default '백화점상품권' not null comment '5개 번호 당첨금 타입',
	lotto_4_gift_type varchar(32) default '문화상품권' not null comment '4개 번호 당첨금 타입',
	lotto_3_gift_type varchar(32) default '문화상품권' not null comment '3개 번호 당첨금 타입'
);

create table if not exists member
(
	seq_no bigint auto_increment
		primary key,
	country_seq_no bigint null,
	member_name varchar(64) null,
	account_type varchar(32) default 'pplus' not null,
	login_id varchar(64) not null,
	password varchar(64) not null,
	member_type varchar(32) not null,
	use_status varchar(32) default 'normal' not null,
	restriction_status varchar(32) default 'none' not null,
	restriction_clear_datetime datetime default (CURRENT_TIMESTAMP) not null,
	nickname varchar(64) null,
	mobile_number varchar(64) null,
	email varchar(128) null,
	zip_code varchar(10) null,
	base_address varchar(128) null,
	join_datetime datetime default (CURRENT_TIMESTAMP) not null,
	join_platform varchar(32) not null,
	verification_media varchar(32) not null,
	recommendation_code varchar(32) null,
	gender varchar(32) null,
	married enum('Y', 'N') null,
	child enum('Y', 'N') null,
	birthday varchar(16) null,
	job varchar(64) null,
	talk_receive_bounds varchar(32) default 'everybody' not null,
	talk_deny_day varchar(64) null,
	talk_deny_start_time time null,
	talk_deny_end_time time null,
	talk_receive enum('Y', 'N') default 'Y' not null,
	talk_push_receive enum('Y', 'N') default 'Y' not null,
	last_login_datetime datetime null,
	login_fail_count int default 0 not null,
	last_login_fail_datetime datetime null,
	contact_list_version bigint null,
	leave_request_datetime datetime null,
	leave_finish_datetime datetime null,
	calculated enum('Y', 'N') default 'N' not null,
	calculated_month date null,
	reg_type varchar(32) default 'piece' not null,
	sendbird_user enum('Y', 'N') default 'N' not null,
	cash bigint default 0 not null,
	bol bigint default 0 not null,
	mod_datetime datetime null,
	modifier_seq_no bigint null,
	profile_seq_no bigint null,
	member_prop text null,
	recommend_unique_key varchar(32) null,
	certification_level smallint default 1 not null,
	board_seq_no bigint not null comment '사용자 게시판 순번',
	lotto_ticket_count int default 0 not null comment '로또 응모권 카운트',
	lotto_default_ticket_count int default 0 null comment '매주 생성되는 로또 참여 티켓 카운트',
	latitude double null comment '위도',
	longitude double null comment '경도',
	constraint boardSeqNo
		unique (board_seq_no),
	constraint login_id_uniq
		unique (login_id),
	constraint mobile_number_uniq
		unique (mobile_number),
	constraint nickname_uniq
		unique (nickname),
	constraint fk_member_country
		foreign key (country_seq_no) references country (seq_no)
			on update set null on delete set null,
	constraint fk_member_profile
		foreign key (profile_seq_no) references attachment (seq_no)
			on update set null on delete set null
)
charset=utf8;

create table if not exists admin
(
	seq_no bigint auto_increment comment '관리자 순번'
		primary key,
	login_id varchar(64) not null comment '로그인 ID',
	password varchar(64) not null comment '로그인 PWD',
	status varchar(32) not null comment '상태',
	use_auth_number enum('Y', 'N') not null comment '인증 번호 사용 여부',
	auth_number varchar(64) null comment '인증 번호',
	name varchar(128) not null comment '이름',
	email varchar(256) not null comment '이메일',
	note varchar(512) null comment '메모',
	reg_datetime datetime not null comment '등록 일시',
	registrant_seq_no bigint null comment '등록자 순번',
	mod_datetime datetime null comment '수정 일시',
	modifier_seq_no bigint null comment '수정자 순번',
	company varchar(128) null comment '회사',
	constraint admin_ibfk_1
		foreign key (seq_no) references member (seq_no)
)
comment '관리자';

create table if not exists admin_work
(
	seq_no bigint auto_increment comment '작업 순번'
		primary key,
	admin_seq_no bigint null comment '관리자 순번',
	work_type varchar(32) not null comment '작업 타입',
	target_seq_no bigint null comment '대상 순번',
	work_desc varchar(1024) null comment '작업 설명',
	work_prop varchar(2048) null comment '작업 속성',
	work_datetime datetime not null comment '작업 일시',
	constraint admin_work_ibfk_1
		foreign key (admin_seq_no) references admin (seq_no)
)
comment '관리 작업';

create index fk_admin_work_1
	on admin_work (admin_seq_no);

create table if not exists admin_work_attachment
(
	work_seq_no bigint not null comment '작업 순번',
	attach_seq_no bigint not null comment '자료 순번',
	primary key (work_seq_no, attach_seq_no),
	constraint admin_work_attachment_ibfk_1
		foreign key (work_seq_no) references admin_work (seq_no),
	constraint admin_work_attachment_ibfk_2
		foreign key (attach_seq_no) references attachment (seq_no)
)
comment '관리 작업 자료';

create index fk_admin_work_attch_2
	on admin_work_attachment (attach_seq_no);

create table if not exists adpc_reward
(
	reward_key varchar(32) not null comment '리워드 키'
		primary key,
	quantity int not null comment '지급 금액',
	campaign_key varchar(32) null comment '캠페인 키',
	reg_datetime datetime not null comment '등록 일시',
	member_seq_no bigint null comment '회원 순번',
	constraint adpc_reward_ibfk_1
		foreign key (member_seq_no) references member (seq_no)
)
comment '애드팝콘 리워드';

create index fk_adpc_member
	on adpc_reward (member_seq_no);

create table if not exists advertise
(
	seq_no bigint auto_increment
		primary key,
	member_seq_no bigint null,
	ad_type varchar(32) default 'article' not null,
	ad_status varchar(32) default 'ready' not null,
	ad_start_datetime datetime not null,
	ad_end_datetime datetime not null,
	total_count int default 0 not null,
	current_count int default 0 not null,
	cost bigint default 0 not null,
	base_price bigint default 0 not null,
	service_reward int default 0 not null,
	reward int default 0 not null,
	is_last enum('Y', 'N') default 'Y' not null,
	ad_prop varchar(1024) null,
	contact_count int default 0 not null,
	like_count int default 0 not null,
	ad_free enum('Y', 'N') default 'N' not null,
	refund_cost bigint default 0 not null,
	refund_reward bigint default 0 not null,
	reg_datetime datetime default (CURRENT_TIMESTAMP) not null,
	constraint fk_ad_member
		foreign key (member_seq_no) references member (seq_no)
)
comment '홍보';

create table if not exists advertise_coupon_template
(
	ad_seq_no bigint not null,
	template_seq_no bigint not null,
	primary key (ad_seq_no, template_seq_no),
	constraint advertise_coupon_template_ibfk_1
		foreign key (ad_seq_no) references advertise (seq_no),
	constraint advertise_coupon_template_ibfk_2
		foreign key (template_seq_no) references coupon_template (seq_no)
);

create index fk_ad_coupon_tmpl_2
	on advertise_coupon_template (template_seq_no);

create table if not exists advertise_like
(
	ad_seq_no bigint not null,
	member_seq_no bigint not null,
	like_datetime datetime default (CURRENT_TIMESTAMP) not null,
	primary key (ad_seq_no, member_seq_no),
	constraint advertise_like_ibfk_1
		foreign key (ad_seq_no) references advertise (seq_no),
	constraint advertise_like_ibfk_2
		foreign key (member_seq_no) references member (seq_no)
);

create index fk_advertise_like_2
	on advertise_like (member_seq_no);

create table if not exists approval
(
	seq_no bigint auto_increment
		primary key,
	amount bigint default 0 not null,
	member_seq_no bigint null,
	status varchar(32) default 'pending' not null comment '승인 상태 - pending: 승인 대기, complete: 승인 완료',
	expire_date datetime null,
	approval_type varchar(32) default 'pay' not null comment '승인 타입 - pay: 결제 승인, cancel : 취소 승인',
	pay_transaction_id varchar(128) null,
	pay_result_code varchar(32) not null,
	pay_result_msg varchar(1024) null,
	auth_transaction_id varchar(128) null,
	auth_result_code varchar(32) null,
	auth_result_msg varchar(1024) null,
	pay_method varchar(16) null,
	order_key varchar(64) null,
	pay_info varchar(1024) null,
	original_result mediumtext null,
	cancel_seq_no bigint null,
	cancel_prop varchar(4096) null,
	reg_datetime datetime default (CURRENT_TIMESTAMP) not null,
	constraint fk_approval_member
		foreign key (member_seq_no) references member (seq_no)
);

create index fk_approval_member_idx
	on approval (member_seq_no);

create table if not exists article
(
	seq_no bigint auto_increment
		primary key,
	board_seq_no bigint not null,
	member_seq_no bigint null,
	subject varchar(256) null,
	memo varchar(8096) null,
	use_contents enum('Y', 'N') default 'N' not null,
	contents longtext null,
	priority int default 1 not null,
	view_count bigint default 0 not null,
	blind enum('Y', 'N') default 'N' not null,
	article_type varchar(20) not null,
	article_prop varchar(4096) null,
	reg_datetime datetime default (CURRENT_TIMESTAMP) not null,
	mod_datetime datetime null,
	constraint fk_article_author
		foreign key (member_seq_no) references member (seq_no),
	constraint fk_article_board
		foreign key (board_seq_no) references board (seq_no)
);

create table if not exists advertise_article
(
	ad_seq_no bigint not null,
	article_seq_no bigint not null,
	primary key (ad_seq_no, article_seq_no),
	constraint advertise_article_ibfk_1
		foreign key (ad_seq_no) references advertise (seq_no),
	constraint advertise_article_ibfk_2
		foreign key (article_seq_no) references article (seq_no)
);

create index fk_ad_article_2
	on advertise_article (article_seq_no);

create index fk_article_author_idx
	on article (member_seq_no);

create index fk_article_board_idx
	on article (board_seq_no);

create table if not exists article_attachment
(
	article_seq_no bigint not null,
	attach_seq_no bigint not null,
	priority int default 1 not null,
	purpose varchar(32) not null,
	primary key (article_seq_no, attach_seq_no),
	constraint fk_article_attachment
		foreign key (article_seq_no) references article (seq_no),
	constraint fk_attachment_article
		foreign key (attach_seq_no) references attachment (seq_no)
);

create index fk_article_attachment_idx
	on article_attachment (article_seq_no);

create index fk_attachment_article_idx
	on article_attachment (attach_seq_no);

create table if not exists auth
(
	seq_no bigint auto_increment
		primary key,
	admin_seq_no bigint not null,
	admin_menu_seq_no bigint not null,
	reg_datetime datetime default (CURRENT_TIMESTAMP) not null,
	constraint admin_seq_no
		unique (admin_seq_no, admin_menu_seq_no),
	constraint auth_ibfk_1
		foreign key (admin_seq_no) references admin (seq_no),
	constraint auth_ibfk_2
		foreign key (admin_menu_seq_no) references admin_menu (seq_no)
);

create index fk_auth_admin_menu
	on auth (admin_menu_seq_no);

create table if not exists buy
(
	seq_no bigint auto_increment comment '상품 구매 순번'
		primary key,
	member_seq_no bigint not null comment '상품 구매 사용자 순번',
	order_id varchar(255) not null comment '결제 주문 아이디',
	pay_method varchar(64) default 'card' not null comment 'PG 결제수단',
	pg varchar(64) default 'inicis' not null comment 'PG사',
	pg_tran_id varchar(255) null comment 'PG사 거래번호 아이디',
	pg_accept_id varchar(255) null comment 'PG사 승인번호 아이디',
	carts varchar(1024) null comment '구매한 장바구니 순번 리스트',
	title varchar(255) default '주문명:결제테스트' not null comment '주문제목',
	buyer_email varchar(128) null comment '구매자 email',
	buyer_name varchar(64) not null comment '구매자 email',
	buyer_tel varchar(32) not null comment '구매자 전화번호',
	buyer_address varchar(128) null comment '구매자 주소',
	buyer_postcode varchar(128) null comment '구매자 우편번호',
	price float not null comment '구매 상품 전체 가격',
	vat float default 0 not null comment '구매 상품 전체 VAT',
	reg_datetime datetime not null comment '구매 시각',
	mod_datetime datetime not null comment '변경 시각',
	process int default 0 not null comment 'ERROR(-2),  DENIED(-1),  WAIT(0),  PAY(1),  CANCEL(2), USE(3), REFUND(4), EXPIRE(5) , CANCEL_WAIT(6), USE_WAIT(7), REFUND_WAIT(8), EXPIRE_WAIT(9), BIZ_CANCEL(10), BIZ_CANCEL_WAIT(11)',
	cash tinyint(1) default 0 not null comment '캐쉬 충전 구매',
	order_type int null comment '0: 매장주문, 1포장주문, 2:배달주문, 3:배송(reserved)',
	order_process int null comment '0: 접수대기, 1:접수완료, 2:완료, 3: 주문최소, 4:배송중(reserved)',
	page_seq_no bigint not null comment 'page(상점) 순번',
	client_address varchar(255) null comment '배달 주문 시 주문 사용자 주소',
	memo varchar(255) null comment '주문자 요청 메모',
	delivery_fee float null comment '배달비',
	book_datetime datetime null comment '포장(예약) 주문 시 예약시각',
	type int default 0 not null comment '0 : 메뉴 오더 상품, 1 : 일반 구매 상품',
	cancel_memo varchar(255) null comment '주문자 요청 취소 메모',
	is_hotdeal tinyint(1) default 0 not null comment '핫딜 상품 여부',
	is_plus tinyint(1) default 0 not null comment '플러스 상품 여부',
	confirm_datetime datetime null comment '주문 접수 확인',
	cancel_datetime datetime null comment '주문 취소 확인',
	complete_datetime datetime null comment '주문 완료 확인',
	order_datetime datetime null comment '오더 상품 주문 시각',
	constraint order_id
		unique (order_id),
	constraint buy_ibfk_1
		foreign key (member_seq_no) references member (seq_no)
);

create index cach
	on buy (cash);

create index is_hotdeal
	on buy (is_hotdeal);

create index is_plus
	on buy (is_plus);

create index member_seq_no
	on buy (member_seq_no);

create index mod_datetime
	on buy (mod_datetime);

create index order_datetime
	on buy (order_datetime);

create index order_process
	on buy (order_process);

create index order_type
	on buy (order_type);

create index page_seq_no
	on buy (page_seq_no);

create index process
	on buy (process);

create index reg_datetime
	on buy (reg_datetime);

create index type
	on buy (type);

create table if not exists buy_callback
(
	seq_no bigint auto_increment comment '상품 구매 callback 순번'
		primary key,
	buy_seq_no bigint not null comment '상품 구매 순번',
	member_seq_no bigint not null comment '상품 구매 사용자 순번',
	order_id varchar(64) not null comment '결제 주문 아이디',
	pg_tran_id varchar(64) not null comment 'bootPay.receipt_id 거래 승인 번호 ',
	pg varchar(64) not null comment 'PG 사',
	pg_name varchar(64) default '' not null comment 'PG 사명',
	method varchar(64) not null comment 'PG 결제 방법',
	method_name varchar(255) default '' not null comment 'PG 결제 방법 상세',
	application_id varchar(64) default '' not null comment 'bootPay.application_id 앱 ID',
	name varchar(255) default '' not null comment 'PG 결제 제목',
	private_key varchar(64) default '' not null comment 'bootPay.private_key 개인 키',
	status char default '0' not null comment 'PG 처리 여부 0 : 미승인, 1:승인',
	price int default 0 not null comment 'PG 결제 가격(String)',
	payment_data blob not null comment '결제 정보',
	reg_datetime datetime not null comment '구매 시각',
	retry_count int default 1 not null comment '콜백 재시도 횟수',
	constraint buy_callback_ibfk_1
		foreign key (member_seq_no) references member (seq_no),
	constraint buy_callback_ibfk_2
		foreign key (buy_seq_no) references buy (seq_no)
);

create index application_id
	on buy_callback (application_id);

create index buy_seq_no
	on buy_callback (buy_seq_no);

create index member_seq_no
	on buy_callback (member_seq_no);

create index method
	on buy_callback (method);

create index order_id
	on buy_callback (order_id);

create index pg
	on buy_callback (pg);

create index pg_tran_id
	on buy_callback (pg_tran_id);

create index private_key
	on buy_callback (private_key);

create index reg_datetime
	on buy_callback (reg_datetime);

create index status
	on buy_callback (status);

create table if not exists buy_goods
(
	seq_no bigint auto_increment comment '구매 상품 상세 순번'
		primary key,
	buy_seq_no bigint not null comment '상품 구매 순번',
	member_seq_no bigint not null comment '구매 상품 순번',
	goods_seq_no bigint not null comment '구매 상품 순번',
	count int default 1 not null comment '구매 상품 갯수',
	goods_prop varchar(4096) null comment '구매 상품 옵션',
	memo varchar(255) null comment '상점에 남기는 메모',
	price float not null comment '구매 상품 가격',
	vat float default 0 not null comment '구매 상품 VAT',
	process int default 0 not null comment 'ERROR(-2),  DENIED(-1),  WAIT(0),  PAY(1),  CANCEL(2), USE(3), REFUND(4), EXPIRE(5) , CANCEL_WAIT(6), USE_WAIT(7), REFUND_WAIT(8), EXPIRE_WAIT(9), BIZ_CANCEL(10), BIZ_CANCEL_WAIT(11)',
	reg_datetime datetime not null comment '결제요청 시각',
	mod_datetime datetime null comment ' 수정 시각',
	pay_datetime datetime null comment ' 결제승인완료 시각',
	cancel_datetime datetime null comment '결제취소 시각',
	use_datetime datetime null comment '사용완료 시각',
	refund_datetime datetime null comment '사용완료 후 환불 시각',
	expire_datetime datetime null comment '결제 후 사용기한 초과 시각',
	page_seq_no bigint not null comment '상품 상점 페이지 순번',
	process_rollback int default 0 not null comment '결체 취소 요청 철회',
	order_type int null comment '0: 매장주문, 1포장주문, 2:배달주문, 3:배송(reserved)',
	order_process int null comment '0: 접수대기, 1:접수완료, 2:완료, 3: 주문최소, 4:배송중(reserved)',
	is_review_exist tinyint(1) default 0 null comment '구매가 리뷰 등 록 여부',
	order_datetime datetime null comment '오더 상품 주문 시각',
	constraint buy_goods_ibfk_1
		foreign key (buy_seq_no) references buy (seq_no)
			on delete cascade,
	constraint buy_goods_ibfk_2
		foreign key (goods_seq_no) references goods (seq_no)
);

create index buy_seq_no
	on buy_goods (buy_seq_no);

create index cancel_datetime
	on buy_goods (cancel_datetime);

create index expire_datetime
	on buy_goods (expire_datetime);

create index goods_seq_no
	on buy_goods (goods_seq_no);

create index is_review_exist
	on buy_goods (is_review_exist);

create index is_review_exist_2
	on buy_goods (is_review_exist);

create index member_seq_no
	on buy_goods (member_seq_no);

create index order_datetime
	on buy_goods (order_datetime);

create index order_process
	on buy_goods (order_process);

create index order_type
	on buy_goods (order_type);

create index page_seq_no
	on buy_goods (page_seq_no);

create index pay_datetime
	on buy_goods (pay_datetime);

create index price
	on buy_goods (price);

create index process
	on buy_goods (process);

create index process_rollback
	on buy_goods (process_rollback);

create index refund_datetime
	on buy_goods (refund_datetime);

create index reg_datetime
	on buy_goods (reg_datetime);

create index use_datetime
	on buy_goods (use_datetime);

create table if not exists buy_goods_process
(
	buy_goods_seq_no bigint not null comment '상품 구매 순번'
		primary key,
	process tinyint default 1 not null comment '1:결제완료,2:사용완료,0:결제취소',
	pay_datetime datetime not null comment '결제완료 시각',
	use_datetime datetime null comment ' 사용완료 시각',
	cancel_datetime datetime null comment '결제취소 시각',
	constraint buy_goods_process_ibfk_1
		foreign key (buy_goods_seq_no) references buy_goods (seq_no)
			on delete cascade
);

create table if not exists comment
(
	seq_no bigint auto_increment
		primary key,
	article_seq_no bigint not null,
	member_seq_no bigint not null,
	parent_seq_no bigint null,
	comment varchar(4096) null,
	comment_prop varchar(1024) null,
	deleted enum('Y', 'N') default 'N' not null,
	blind enum('Y', 'N') default 'N' not null,
	group_seq_no bigint null,
	depth int default 1 not null,
	sort_num int default 1 not null,
	reg_datetime datetime default (CURRENT_TIMESTAMP) not null,
	constraint fk_comment_article
		foreign key (article_seq_no) references article (seq_no),
	constraint fk_comment_author
		foreign key (member_seq_no) references member (seq_no)
);

create index fk_comment_article_idx
	on comment (article_seq_no);

create index fk_comment_author_idx
	on comment (member_seq_no);

create definer = root@`112.216.128.%` trigger before_insert_comment
	before insert
	on comment
	for each row
	BEGIN
   DECLARE group_seq_no BIGINT(19);

   IF NEW.parent_seq_no IS NULL THEN
     SET group_seq_no = (SELECT AUTO_INCREMENT FROM information_schema.TABLES WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='comment');
     SET NEW.group_seq_no = group_seq_no;
   END IF;

 END;

create table if not exists contact
(
	member_seq_no bigint not null,
	mobile_number varchar(64) not null,
	reg_datetime datetime default (CURRENT_TIMESTAMP) not null,
	mod_datetime datetime null,
	primary key (member_seq_no, mobile_number),
	constraint fk_contact_member
		foreign key (member_seq_no) references member (seq_no)
);

create index fk_contact_member_idx
	on contact (member_seq_no);

create index mobile_number_idx
	on contact (mobile_number);

create table if not exists contract
(
	seq_no bigint not null
		primary key,
	start_date char(8) null,
	end_date char(8) null,
	reg_datetime datetime null,
	member_seq_no bigint not null,
	agent_seq_no bigint not null,
	constraint contract_ibfk_1
		foreign key (member_seq_no) references member (seq_no),
	constraint contract_ibfk_2
		foreign key (agent_seq_no) references agent (seq_no)
);

create index fk_contract_agent
	on contract (agent_seq_no);

create index fk_contract_mem
	on contract (member_seq_no);

create table if not exists coupon
(
	seq_no bigint auto_increment
		primary key,
	template_seq_no bigint not null,
	member_seq_no bigint not null,
	code varchar(64) not null,
	get_method varchar(32) default 'download' not null,
	get_datetime datetime null,
	use_datetime datetime null,
	status varchar(32) default 'ready' not null,
	constraint fk_coupon_member
		foreign key (member_seq_no) references member (seq_no),
	constraint fk_coupon_template
		foreign key (template_seq_no) references coupon_template (seq_no)
);

create table if not exists advertise_coupon
(
	ad_seq_no bigint not null,
	coupon_seq_no bigint not null,
	primary key (ad_seq_no, coupon_seq_no),
	constraint advertise_coupon_ibfk_1
		foreign key (ad_seq_no) references advertise (seq_no),
	constraint advertise_coupon_ibfk_2
		foreign key (coupon_seq_no) references coupon (seq_no)
);

create index fk_ad_coupon_2
	on advertise_coupon (coupon_seq_no);

create index fk_coupon_member_idx
	on coupon (member_seq_no);

create index fk_coupon_template_idx
	on coupon (template_seq_no);

create table if not exists device
(
	seq_no bigint auto_increment
		primary key,
	device_id varchar(128) not null,
	platform varchar(32) not null,
	last_access_datetime datetime default (CURRENT_TIMESTAMP) not null,
	member_seq_no bigint null,
	device_prop varchar(4096) null,
	constraint fk_device_member
		foreign key (member_seq_no) references member (seq_no)
);

create index fk_device_member_idx
	on device (member_seq_no);

create table if not exists friend
(
	member_seq_no bigint not null,
	mobile_number varchar(64) not null,
	friend_seq_no bigint not null,
	primary key (member_seq_no, mobile_number),
	constraint fk_friend_friend
		foreign key (friend_seq_no) references member (seq_no),
	constraint fk_friend_member
		foreign key (member_seq_no) references member (seq_no)
);

create index fk_friend_friend_idx
	on friend (friend_seq_no);

create index fk_friend_member_idx
	on friend (member_seq_no);

create index mobile_number_key
	on friend (mobile_number);

create table if not exists installed_app
(
	device_seq_no bigint not null,
	app_key varchar(64) not null,
	version varchar(32) not null,
	push_registration_id varchar(256) null,
	push_activate enum('Y', 'N') default 'Y' not null,
	push_receive char(16) default '1111111111111111' not null,
	installed_prop varchar(8192) null,
	reg_datetime datetime default (CURRENT_TIMESTAMP) not null,
	installed enum('Y', 'N') default 'Y' not null,
	primary key (device_seq_no, app_key),
	constraint fk_installed_app_app
		foreign key (app_key) references app (app_key),
	constraint fk_installed_app_device
		foreign key (device_seq_no) references device (seq_no)
)
charset=utf8;

create index fk_installed_app_app_idx
	on installed_app (app_key);

create index fk_installed_app_device_idx
	on installed_app (device_seq_no);

create table if not exists interest
(
	member_seq_no bigint not null,
	category_seq_no bigint not null,
	reg_datetime datetime default (CURRENT_TIMESTAMP) not null,
	primary key (member_seq_no, category_seq_no),
	constraint fk_interest_category
		foreign key (category_seq_no) references category (seq_no),
	constraint fk_interest_member
		foreign key (member_seq_no) references member (seq_no)
);

create index fk_interest_category_idx
	on interest (category_seq_no);

create index fk_interest_member_idx
	on interest (member_seq_no);

create index fk_member_country_idx
	on member (country_seq_no);

create index fk_member_profile_idx
	on member (profile_seq_no);

create index join_datetime_idx
	on member (join_datetime);

create index latitude
	on member (latitude);

create index longitude
	on member (longitude);

create index lotto_default_ticket_count
	on member (lotto_default_ticket_count);

create index lotto_ticket_count
	on member (lotto_ticket_count);

create index member_email_idx
	on member (email);

create table if not exists member_address
(
	member_seq_no bigint not null,
	address varchar(128) not null,
	primary key (member_seq_no, address),
	constraint member_address_ibfk_1
		foreign key (member_seq_no) references member (seq_no)
);

create table if not exists member_board
(
	member_seq_no bigint not null comment '글 작성자 순번',
	board_seq_no bigint not null comment '게시판 순번',
	primary key (member_seq_no, board_seq_no),
	constraint member_board_ibfk_1
		foreign key (member_seq_no) references member (seq_no)
			on delete cascade,
	constraint member_board_ibfk_2
		foreign key (board_seq_no) references board (seq_no)
			on delete cascade
);

create index board_seq_no
	on member_board (board_seq_no);

create table if not exists member_hashtag
(
	member_seq_no bigint not null comment '사용자 순번'
		primary key,
	hashtag varchar(255) not null comment '사용자 선택 해쉬태그'
);

create table if not exists member_mobile
(
	seq_no bigint not null,
	mobile_number varchar(64) not null,
	reg_datetime datetime default (CURRENT_TIMESTAMP) not null,
	primary key (seq_no, mobile_number),
	constraint fk_member_mobile
		foreign key (seq_no) references member (seq_no)
)
charset=utf8;

create index member_mobile_key1
	on member_mobile (mobile_number);

create table if not exists member_transfer
(
	seqNo bigint not null
		primary key,
	accountHolderName varchar(255) null,
	accountId varchar(255) null,
	bankCodeStd varchar(255) null,
	bankCodeSub varchar(255) null,
	bankName varchar(255) null,
	bankTranId varchar(255) null,
	pageSeqNo bigint null,
	reg_datetime datetime(6) null,
	tranAccountAlias varchar(255) null,
	tranAccountHolderName varchar(255) null,
	tranAccountId varchar(255) null,
	tranBankCodeStd varchar(255) null,
	tranBankCodeSub varchar(255) null,
	tranBankName varchar(255) null,
	tran_amount float null,
	tran_datetime datetime(6) null
);

create table if not exists mobilegift
(
	seq_no bigint auto_increment
		primary key,
	name varchar(256) null,
	code varchar(64) null,
	company_name varchar(128) null,
	company_code varchar(64) null,
	user_price bigint default 0 not null,
	sale_price bigint default 0 not null,
	use_area varchar(2048) null,
	tax enum('Y', 'N') default 'Y' not null,
	use_limit varchar(2048) null,
	use_note varchar(2048) null,
	output_type varchar(32) null,
	sale enum('Y', 'N') default 'Y' not null,
	old_pcode varchar(64) null,
	offer_price bigint default 0 not null,
	use_term varchar(32) null,
	inc_vat enum('Y', 'N') default 'Y' not null,
	base_image varchar(512) null,
	view_image1 varchar(512) null,
	view_image2 varchar(512) null,
	view_image3 varchar(512) null,
	detail_image varchar(512) null,
	gift_prop varchar(2048) null,
	mod_datetime datetime default (CURRENT_TIMESTAMP) not null,
	priority int default 1 not null
);

create table if not exists mobilegift_category
(
	seq_no bigint auto_increment
		primary key,
	name varchar(64) not null,
	hierarchy varchar(16) null,
	registrant_seq_no bigint not null,
	reg_datetime datetime not null
);

create table if not exists mobilegift_image
(
	mobilegift_seq_no bigint not null,
	seq_no int not null,
	code varchar(64) null,
	path varchar(512) null,
	width varchar(32) null,
	height varchar(32) null,
	memo varchar(1024) null,
	primary key (mobilegift_seq_no, seq_no),
	constraint mobilegift_image_ibfk_1
		foreign key (mobilegift_seq_no) references mobilegift (seq_no)
);

create table if not exists mobilegift_inc_category
(
	category_seq_no bigint not null,
	mobilegift_seq_no bigint not null,
	registrant_seq_no bigint not null,
	reg_datetime datetime default (CURRENT_TIMESTAMP) not null,
	primary key (category_seq_no, mobilegift_seq_no),
	constraint mobilegift_inc_category_ibfk_1
		foreign key (category_seq_no) references mobilegift_category (seq_no),
	constraint mobilegift_inc_category_ibfk_2
		foreign key (mobilegift_seq_no) references mobilegift (seq_no)
);

create index fk_inc_category_mobilegift
	on mobilegift_inc_category (mobilegift_seq_no);

create table if not exists mobilegift_purchase
(
	seq_no bigint auto_increment
		primary key,
	status varchar(32) default 'pending' not null,
	member_seq_no bigint not null,
	mobilegift_seq_no bigint not null,
	count_per_target smallint null,
	total_cost bigint default 0 not null,
	pg_cost bigint default 0 not null,
	approval_seq_no bigint null,
	target_count int default 1 not null,
	succ_count int default 0 not null,
	fail_count int default 0 not null,
	inc_me enum('Y', 'N') default 'N' not null,
	msg varchar(4096) null,
	main_name varchar(64) null,
	main_mobile varchar(64) null,
	purchase_prop varchar(2048) null,
	reg_datetime datetime default (CURRENT_TIMESTAMP) not null,
	constraint fk_mobilegift_purchase_approval
		foreign key (approval_seq_no) references approval (seq_no),
	constraint mobilegift_purchase_ibfk_1
		foreign key (member_seq_no) references member (seq_no),
	constraint mobilegift_purchase_ibfk_2
		foreign key (mobilegift_seq_no) references mobilegift (seq_no)
);

create index R_bilegif_bil_15e
	on mobilegift_purchase (mobilegift_seq_no);

create index R_mber_bil_b0d
	on mobilegift_purchase (member_seq_no);

create table if not exists nav_map
(
	id int auto_increment
		primary key,
	name varchar(50) null,
	lft int null,
	rgt int null
);

create table if not exists note
(
	seq_no bigint auto_increment
		primary key,
	member_seq_no bigint null,
	contents varchar(8192) not null,
	reg_datetime datetime default (CURRENT_TIMESTAMP) not null,
	constraint note_ibfk_1
		foreign key (member_seq_no) references member (seq_no)
);

create index fk_note_member
	on note (member_seq_no);

create table if not exists note_receiver
(
	note_seq_no bigint not null,
	member_seq_no bigint not null,
	primary key (note_seq_no, member_seq_no),
	constraint note_receiver_ibfk_1
		foreign key (note_seq_no) references note (seq_no),
	constraint note_receiver_ibfk_2
		foreign key (member_seq_no) references member (seq_no)
);

create index fk_note_receiver_member
	on note_receiver (member_seq_no);

create table if not exists notice
(
	seq_no bigint auto_increment
		primary key,
	status varchar(32) default 'active' not null comment '자릿수 상태 - active:활성화, deactive:비활성화',
	start_datetime datetime not null comment '게시 시작시간',
	end_datetime datetime not null comment '게시 종료시간',
	subject varchar(256) not null,
	contents longtext not null,
	priority int default 100 not null comment '우선순위',
	path varchar(512) null comment '프로모션 경로',
	reg_datetime datetime default (CURRENT_TIMESTAMP) not null,
	registrant_seq_no bigint not null,
	mod_datetime datetime null,
	modifier_seq_no bigint null
);

create table if not exists app_notice
(
	app_key varchar(64) not null,
	notice_seq_no bigint not null,
	primary key (app_key, notice_seq_no),
	constraint app_notice_ibfk_1
		foreign key (app_key) references app (app_key),
	constraint app_notice_ibfk_2
		foreign key (notice_seq_no) references notice (seq_no)
)
charset=utf8;

create index fk_app_notice_notice
	on app_notice (notice_seq_no);

create table if not exists offer
(
	seq_no bigint auto_increment
		primary key,
	req_seq_no bigint null,
	expire_datetime datetime null,
	expired enum('Y', 'N') default 'N' not null,
	category_seq_no bigint not null,
	member_seq_no bigint not null,
	res_count int default 0 not null,
	offer_prop varchar(4096) null,
	reg_datetime datetime default (CURRENT_TIMESTAMP) not null,
	constraint fk_offer_article
		foreign key (req_seq_no) references article (seq_no),
	constraint fk_offer_category
		foreign key (category_seq_no) references category (seq_no),
	constraint offer_ibfk_1
		foreign key (member_seq_no) references member (seq_no)
)
charset=utf8;

create index R_mber_fer_76d
	on offer (member_seq_no);

create index fk_offer_article_idx
	on offer (req_seq_no);

create index fk_offer_category_idx
	on offer (category_seq_no);

create table if not exists offer_response
(
	offer_seq_no bigint not null,
	res_seq_no bigint not null,
	primary key (offer_seq_no, res_seq_no),
	constraint fk_offer_response
		foreign key (offer_seq_no) references offer (seq_no),
	constraint fk_offer_response_article
		foreign key (res_seq_no) references article (seq_no)
);

create index fk_offer_response_article_idx
	on offer_response (res_seq_no);

create index fk_offer_response_idx
	on offer_response (offer_seq_no);

create table if not exists page
(
	seq_no bigint auto_increment
		primary key,
	member_seq_no bigint not null,
	coop_seq_no bigint null,
	coop_status varchar(32) default 'normal' not null,
	status varchar(32) default 'normal' not null,
	page_name varchar(64) not null,
	code varchar(32) null,
	phone_number varchar(20) null,
	open_bounds varchar(32) default 'everybody' not null,
	zip_code varchar(10) null,
	road_address varchar(128) null,
	road_detail_address varchar(128) null,
	parcel_address varchar(128) null,
	parcel_detail_address varchar(128) null,
	latitude double null,
	longitude double null,
	catchphrase varchar(128) null,
	introduction varchar(1024) null,
	category_text varchar(128) null,
	today_view_count bigint default 0 not null,
	total_view_count bigint default 0 not null,
	blind enum('Y', 'N') default 'Y' not null,
	talk_receive_bounds varchar(32) default 'everybody' null,
	talk_deny_day varchar(64) null,
	talk_deny_start_time time null,
	talk_deny_end_time time null,
	customer_count int default 0 not null,
	plus_count int default 0 not null,
	main_movie_url varchar(1024) null,
	page_prop text null,
	reg_datetime datetime null,
	mod_datetime datetime null,
	page_type varchar(32) default 'store' not null,
	page_level int default 1 not null,
	modifier_seq_no bigint null,
	profile_seq_no bigint null,
	bg_seq_no bigint null,
	valuation_count bigint default 0 not null,
	valuation_point bigint default 0 not null,
	offer_res_datetime datetime null,
	virtual_page enum('Y', 'N') default 'N' not null,
	search_keyword varchar(512) null,
	auth_code varchar(8) null,
	incorrect_auth_code_count int default 0 not null,
	agent_seq_no bigint null,
	recommendation_code varchar(32) null,
	settlement_url varchar(512) null,
	main_goods_seq_no bigint null,
	is_seller tinyint(1) default 1 null comment '상품판배 가능 여부',
	is_link tinyint(1) default 0 not null comment '링크 페이지 여부',
	homepage_link varchar(1024) null comment '홈 페이지 링크',
	hashtag varchar(255) null comment '해쉬태그',
	thema_seq_no bigint null comment '테마 카테고리 순번',
	is_holiday_closed tinyint(1) default 0 not null comment '휴일 휴무 유무',
	delivery_radius double null comment '배달 반경',
	is_shop_orderable tinyint(1) default 0 null comment '매자 결제 가능여부',
	is_packing_orderable tinyint(1) default 0 not null comment '포장주문',
	is_delivery_orderable tinyint(1) default 0 not null comment '배달주문',
	delivery_fee float null comment '배달비',
	delivery_min_price float null comment '배달가능 최소 주문금액',
	is_parking_available tinyint(1) default 0 not null comment '주차가능여부',
	is_valet_parking_available tinyint(1) default 0 not null comment '발렛주차가능여부',
	is_chain tinyint(1) default 0 not null comment '체인점 여부',
	is_delivery tinyint(1) default 0 not null comment '배달대행 여부',
	parent_seq_no bigint null comment '체인점 지점인 경우만 본점 페이지 순번 값이 있고 그외에는 null',
	use_prnumber tinyint(1) default 0 null comment 'prnumber 사용여부',
	constraint fk_page_background
		foreign key (bg_seq_no) references attachment (seq_no)
			on update set null on delete set null,
	constraint fk_page_cooperation
		foreign key (coop_seq_no) references cooperation (seq_no)
			on update set null on delete set null,
	constraint fk_page_member
		foreign key (member_seq_no) references member (seq_no),
	constraint fk_page_profile
		foreign key (profile_seq_no) references attachment (seq_no)
			on update set null on delete set null,
	constraint page_ibfk_1
		foreign key (agent_seq_no) references agent (seq_no),
	constraint page_ibfk_3
		foreign key (thema_seq_no) references category (seq_no)
			on delete set null,
	constraint page_ibfk_4
		foreign key (main_goods_seq_no) references goods (seq_no)
			on delete set null
)
charset=utf8;

create table if not exists bank_transfer
(
	seq_no bigint auto_increment comment '출금 계좌 처리 순번'
		primary key,
	page_seq_no bigint not null comment '입금기관 상점 페이지 순번',
	bank_code_std varchar(8) not null comment '입금기관 표준 코드 ex:097',
	bank_code_sub varchar(32) not null comment '입금기관 점별코드 ex:1230001',
	bank_name varchar(64) not null comment '입금기관명',
	bank_account_id varchar(128) not null comment '입금계좌번호',
	bank_account_holder_name varchar(32) not null comment '수취인 성명',
	bank_tran_id varchar(255) null comment '출금 거래 고유번호',
	tran_bank_code_std varchar(8) not null comment '출금기관 표준 코드 ex:098',
	tran_bank_code_sub varchar(32) not null comment '출금기관 점별코드 ex:1230001',
	tran_bank_name varchar(64) not null comment '출금기관명',
	tran_bank_account_id varchar(128) not null comment '출금계좌번호',
	tran_bank_account_holder_name varchar(32) not null comment '송금인 성명',
	tran_bank_account_alias varchar(64) not null comment '출금계좌별명',
	tran_datetime datetime null comment '출금 거래 시각',
	tran_amount float not null comment '출금 거래금액',
	reg_datetime datetime not null comment '출금 정보 등록 시각',
	constraint bank_transfer_ibfk_1
		foreign key (page_seq_no) references page (seq_no)
);

create index page_seq_no
	on bank_transfer (page_seq_no);

create table if not exists bol_history
(
	seq_no bigint auto_increment
		primary key,
	member_seq_no bigint not null,
	page_seq_no bigint null,
	primary_type varchar(32) default 'decrease' not null,
	secondary_type varchar(32) default 'giftbol' not null,
	refund_status varchar(32) null,
	amount bigint not null,
	subject varchar(256) not null,
	target_type varchar(32) null,
	target_seq_no bigint null,
	history_prop varchar(4096) null,
	reg_datetime datetime default (CURRENT_TIMESTAMP) not null,
	is_lotto_ticket tinyint(1) default 0 not null comment '로또 티켓 인지 여부',
	constraint fk_bol_history_member
		foreign key (member_seq_no) references member (seq_no),
	constraint fk_bol_history_page
		foreign key (page_seq_no) references page (seq_no)
);

create index fk_bol_history_member_idx
	on bol_history (member_seq_no);

create index fk_bol_history_page_idx
	on bol_history (page_seq_no);

create table if not exists bol_history_target
(
	member_seq_no bigint not null,
	history_seq_no bigint not null,
	amount bigint not null,
	received enum('Y', 'N') default 'N' not null,
	recv_datetime datetime null,
	primary key (member_seq_no, history_seq_no),
	constraint fk_bol_target_history
		foreign key (history_seq_no) references bol_history (seq_no),
	constraint fk_bol_target_member
		foreign key (member_seq_no) references member (seq_no)
);

create index fk_bol_target_history_idx
	on bol_history_target (history_seq_no);

create index fk_bol_target_member_idx
	on bol_history_target (member_seq_no);

create table if not exists bol_refund
(
	seq_no bigint not null,
	process_seq_no int not null,
	prev_status varchar(32) null,
	proc_status varchar(32) null,
	reason varchar(1024) null,
	reg_datetime datetime default (CURRENT_TIMESTAMP) not null,
	registrant_seq_no bigint not null,
	primary key (seq_no, process_seq_no),
	constraint fk_bol_refund_history
		foreign key (seq_no) references bol_history (seq_no)
);

create table if not exists cart
(
	seq_no bigint auto_increment comment '장바구니 상품 순번'
		primary key,
	member_seq_no bigint not null comment '사용자 순번',
	goods_seq_no bigint not null comment '구매 상품 순번',
	count int default 1 not null comment '구매 상품 갯수',
	goods_prop varchar(4096) null comment '구매 상품 옵션',
	reg_datetime datetime not null comment '장바구니 상품 등록 시각',
	mod_datetime datetime not null comment '장바구니 상품 변경 시각',
	page_seq_no bigint not null comment '상품 상점 페이지 순번',
	constraint cart_ibfk_1
		foreign key (member_seq_no) references member (seq_no)
			on delete cascade,
	constraint cart_ibfk_2
		foreign key (goods_seq_no) references goods (seq_no)
			on delete cascade,
	constraint cart_ibfk_3
		foreign key (page_seq_no) references page (seq_no)
			on delete cascade
);

create index goods_seq_no
	on cart (goods_seq_no);

create index member_seq_no
	on cart (member_seq_no);

create index page_seq_no
	on cart (page_seq_no);

create table if not exists cash_history
(
	seq_no bigint auto_increment
		primary key,
	member_seq_no bigint not null,
	page_seq_no bigint null,
	primary_type varchar(32) default 'decrease' not null,
	secondary_type varchar(32) default 'buy' not null,
	refund_status varchar(32) null,
	subject varchar(256) not null,
	target_type varchar(32) null,
	target_seq_no bigint null,
	amount bigint not null,
	history_prop varchar(4096) null,
	reg_datetime datetime default (CURRENT_TIMESTAMP) not null,
	constraint cash_history_ibfk_1
		foreign key (page_seq_no) references page (seq_no),
	constraint cash_history_ibfk_2
		foreign key (member_seq_no) references member (seq_no)
);

create table if not exists cash_approval
(
	cash_seq_no bigint not null,
	approval_seq_no bigint not null,
	primary key (cash_seq_no, approval_seq_no),
	constraint cash_approval_ibfk_1
		foreign key (cash_seq_no) references cash_history (seq_no),
	constraint cash_approval_ibfk_2
		foreign key (approval_seq_no) references approval (seq_no)
);

create index fk_cash_approval_approval
	on cash_approval (approval_seq_no);

create index fk_cash_history_member
	on cash_history (member_seq_no);

create index fk_cash_history_page
	on cash_history (page_seq_no);

create table if not exists cash_refund
(
	seq_no bigint not null,
	process_seq_no int not null,
	prev_status varchar(32) null,
	proc_status varchar(32) null,
	reason varchar(1024) null,
	reg_datetime datetime default (CURRENT_TIMESTAMP) not null,
	registrant_seq_no bigint not null,
	primary key (seq_no, process_seq_no),
	constraint cash_refund_ibfk_1
		foreign key (seq_no) references cash_history (seq_no)
);

create table if not exists category_page
(
	category_seq_no bigint not null,
	page_seq_no bigint not null
		primary key,
	reg_datetime datetime default (CURRENT_TIMESTAMP) not null,
	registrant_seq_no bigint not null,
	constraint fk_category_page
		foreign key (category_seq_no) references category (seq_no),
	constraint fk_page_category
		foreign key (page_seq_no) references page (seq_no)
);

create index fk_category_page_idx
	on category_page (category_seq_no);

create index fk_page_category_idx
	on category_page (page_seq_no);

create table if not exists coupon_page
(
	page_seq_no bigint not null,
	coupon_seq_no bigint not null,
	primary key (page_seq_no, coupon_seq_no),
	constraint fk_coupon_page1
		foreign key (page_seq_no) references page (seq_no),
	constraint fk_coupon_page2
		foreign key (coupon_seq_no) references coupon (seq_no)
);

create index fk_coupon_page_idx1
	on coupon_page (page_seq_no);

create index fk_coupon_page_idx2
	on coupon_page (coupon_seq_no);

create table if not exists customer
(
	seq_no bigint auto_increment
		primary key,
	page_seq_no bigint not null,
	cust_name varchar(64) not null,
	mobile_number varchar(64) not null,
	input_type varchar(32) default 'contact' not null,
	target_seq_no bigint null,
	cust_prop varchar(4096) null,
	status varchar(32) default 'active' not null,
	mkt_config char(32) default '11111111111111111111111111111111' not null,
	reg_datetime datetime default (CURRENT_TIMESTAMP) not null,
	mod_datetime datetime null,
	constraint fk_customer_page
		foreign key (page_seq_no) references page (seq_no),
	constraint fk_customer_target
		foreign key (target_seq_no) references member (seq_no)
);

create index fk_customer_page_idx
	on customer (page_seq_no);

create index fk_customer_target_idx
	on customer (target_seq_no);

create index idx_customer_mobile
	on customer (mobile_number);

create index idx_customer_mobile2
	on customer (page_seq_no, mobile_number);

create table if not exists customergroup
(
	seq_no bigint auto_increment
		primary key,
	page_seq_no bigint not null,
	group_name varchar(64) null,
	default_group enum('Y', 'N') default 'N' not null,
	priority int default 1 not null,
	reg_datetime datetime default (CURRENT_TIMESTAMP) not null,
	constraint fk_customergroup_page
		foreign key (page_seq_no) references page (seq_no)
);

create table if not exists customer_group
(
	group_seq_no bigint not null,
	cust_seq_no bigint not null,
	primary key (group_seq_no, cust_seq_no),
	constraint fk_customer_group
		foreign key (cust_seq_no) references customer (seq_no),
	constraint fk_group_customer
		foreign key (group_seq_no) references customergroup (seq_no)
);

create index fk_customer_group_idx
	on customer_group (cust_seq_no);

create index fk_group_customer_idx
	on customer_group (group_seq_no);

create index fk_customergroup_page_idx
	on customergroup (page_seq_no);

create table if not exists delivery
(
	seq_no bigint auto_increment comment '배달 주문 고유 순번'
		primary key,
	agent_seq_no bigint not null comment 'POS 대행사 순번',
	company_seq_no bigint not null comment '배달대행 회사 고유 순번',
	page_seq_no bigint not null comment '상점 고유 순번',
	id varchar(64) null comment '배달 주문 아이디',
	reg_datetime datetime not null comment '배달 주문 시각',
	mod_datetime datetime not null comment '수정 시각',
	client_address varchar(128) not null comment '고객 주소',
	client_tel varchar(32) not null comment '고객 전화번호',
	client_memo varchar(1024) null comment '고객 메모',
	total_price float not null comment '주문 총 금액',
	payment varchar(32) not null comment '결제 형태 : 현금, 선결제, 카드 3',
	constraint agent_seq_no
		unique (agent_seq_no, company_seq_no, id),
	constraint delivery_ibfk_1
		foreign key (agent_seq_no) references agent (seq_no)
			on delete cascade,
	constraint delivery_ibfk_2
		foreign key (company_seq_no) references delivery_company (seq_no)
			on delete cascade,
	constraint delivery_ibfk_3
		foreign key (page_seq_no) references page (seq_no)
			on delete cascade
);

create index company_seq_no
	on delivery (company_seq_no);

create index page_seq_no
	on delivery (page_seq_no);

create index reg_datetime
	on delivery (reg_datetime);

create index total_price
	on delivery (total_price);

create table if not exists delivery_goods
(
	seq_no bigint auto_increment comment '배달 주문 상품 순번'
		primary key,
	delivery_seq_no bigint not null comment '배달 주문 고유 순번',
	name varchar(64) not null comment '상품/음식 명',
	count int null comment '상품 수량',
	price float null comment '상품 가격',
	constraint delivery_goods_ibfk_1
		foreign key (delivery_seq_no) references delivery (seq_no)
			on delete cascade
);

create index count
	on delivery_goods (count);

create index delivery_seq_no
	on delivery_goods (delivery_seq_no);

create index price
	on delivery_goods (price);

create table if not exists fangroup
(
	seq_no bigint auto_increment
		primary key,
	name varchar(128) not null,
	sort_num int default 1 not null,
	reg_datetime datetime default (CURRENT_TIMESTAMP) not null,
	mod_datetime datetime null,
	page_seq_no bigint null,
	default_group enum('Y', 'N') default 'N' not null,
	group_type varchar(32) default 'user' not null,
	constraint fangroup_ibfk_1
		foreign key (page_seq_no) references page (seq_no)
);

create index fk_fangroup_page
	on fangroup (page_seq_no);

create table if not exists goods_like
(
	member_seq_no bigint not null comment '사용자 순번',
	page_seq_no bigint not null comment '찜 상품 페이지 순번',
	goods_seq_no bigint not null comment '찜 상품 순번',
	status int default 0 not null comment '0 : not like, 1: like(찜)',
	reg_datetime datetime default (CURRENT_TIMESTAMP) not null comment '등록 시각',
	primary key (member_seq_no, page_seq_no, goods_seq_no),
	constraint goods_like_ibfk_1
		foreign key (member_seq_no) references member (seq_no),
	constraint goods_like_ibfk_2
		foreign key (page_seq_no) references page (seq_no),
	constraint goods_like_ibfk_3
		foreign key (goods_seq_no) references goods (seq_no)
			on delete cascade
);

create index goods_seq_no
	on goods_like (goods_seq_no);

create index page_seq_no
	on goods_like (page_seq_no);

create index reg_datetime
	on goods_like (reg_datetime);

create table if not exists goods_like2
(
	seq_no bigint auto_increment comment '찜 순번'
		primary key,
	member_seq_no bigint not null comment '사용자 순번',
	page_seq_no bigint not null comment '찜 상품 페이지 순번',
	goods_seq_no bigint not null comment '찜 상품 순번',
	status int default 0 not null comment '0 : not like, 1: like(찜)',
	reg_datetime datetime not null comment '등록 시각',
	constraint member_seq_no
		unique (member_seq_no, page_seq_no, goods_seq_no),
	constraint goods_like2_ibfk_1
		foreign key (member_seq_no) references member (seq_no),
	constraint goods_like2_ibfk_2
		foreign key (page_seq_no) references page (seq_no),
	constraint goods_like2_ibfk_3
		foreign key (goods_seq_no) references goods (seq_no)
			on delete cascade
)
charset=utf8;

create index goods_seq_no
	on goods_like2 (goods_seq_no);

create index page_seq_no
	on goods_like2 (page_seq_no);

create table if not exists goods_review
(
	seq_no bigint auto_increment comment '상품 리뷰 순번'
		primary key,
	member_seq_no bigint not null comment '구매 사용자 순번',
	goods_seq_no bigint not null comment '구매 상품 순번',
	review varchar(1024) not null comment '구매 상품 리뷰',
	eval tinyint default 5 not null comment '구매 상품 평가 점수:1-10',
	reg_datetime datetime not null comment '등록시각',
	mod_datetime datetime not null comment '변경시각',
	attachments varchar(1024) null comment '첨부 이미지 id 리 스트 : json_prop',
	page_seq_no bigint not null comment '상품 상점 페이지 순번',
	buy_goods_seq_no bigint not null comment '구매 상품 순번',
	constraint goods_review_ibfk_1
		foreign key (member_seq_no) references member (seq_no),
	constraint goods_review_ibfk_2
		foreign key (page_seq_no) references page (seq_no),
	constraint goods_review_ibfk_3
		foreign key (buy_goods_seq_no) references buy_goods (seq_no),
	constraint goods_review_ibfk_4
		foreign key (goods_seq_no) references goods (seq_no)
			on delete cascade
);

create index buy_goods_seq_no
	on goods_review (buy_goods_seq_no);

create index eval
	on goods_review (eval);

create index goods_seq_no
	on goods_review (goods_seq_no);

create index member_seq_no
	on goods_review (member_seq_no);

create index page_seq_no
	on goods_review (page_seq_no);

create table if not exists incorrect_auth_code
(
	seq_no bigint auto_increment
		primary key,
	member_seq_no bigint not null,
	page_seq_no bigint not null,
	reg_datetime datetime default (CURRENT_TIMESTAMP) not null,
	constraint incorrect_auth_code_ibfk_1
		foreign key (member_seq_no) references member (seq_no),
	constraint incorrect_auth_code_ibfk_2
		foreign key (page_seq_no) references page (seq_no)
);

create index fk_incorrect_member
	on incorrect_auth_code (member_seq_no);

create index fk_incorrect_page
	on incorrect_auth_code (page_seq_no);

create table if not exists member_page_action
(
	member_seq_no bigint not null,
	page_seq_no bigint not null,
	recv_review_bol enum('Y', 'N') default 'N' null,
	review_article_no bigint null,
	use_count int default 0 not null,
	primary key (member_seq_no, page_seq_no),
	constraint member_page_actioin_ibfk_1
		foreign key (member_seq_no) references member (seq_no),
	constraint member_page_actioin_ibfk_2
		foreign key (page_seq_no) references page (seq_no)
);

create index fk_action_page
	on member_page_action (page_seq_no);

create table if not exists msg
(
	seq_no bigint auto_increment
		primary key,
	input_type varchar(32) default 'system' not null,
	msg_type varchar(32) default 'lms' not null,
	reserved enum('Y', 'N') default 'N' not null,
	reserve_date datetime null,
	status varchar(32) default 'ready' not null,
	subject varchar(128) null,
	contents varchar(8192) null,
	include_me enum('Y', 'N') default 'N' not null,
	move_type1 varchar(32) null,
	move_type2 varchar(32) null,
	move_seq_no bigint null,
	move_string varchar(512) null,
	msg_prop varchar(4096) null,
	target_count int default 0 not null,
	succ_count int default 0 not null,
	fail_count int default 0 not null,
	read_count int default 0 not null,
	pay_type varchar(32) default 'none' not null,
	total_price bigint default 0 not null,
	refund_price bigint default 0 not null,
	reg_datetime datetime default (CURRENT_TIMESTAMP) not null,
	complete_datetime datetime null,
	member_seq_no bigint not null,
	page_seq_no bigint null,
	constraint fk_msg_member
		foreign key (member_seq_no) references member (seq_no),
	constraint fk_msg_page
		foreign key (page_seq_no) references page (seq_no)
);

create index fk_msg_member_idx
	on msg (member_seq_no);

create index fk_msg_page_idx
	on msg (page_seq_no);

create table if not exists msg_reject_number
(
	reject_number varchar(64) not null,
	reg_datetime datetime default (CURRENT_TIMESTAMP) not null,
	page_seq_no bigint not null,
	primary key (reject_number, page_seq_no),
	constraint msg_reject_number_ibfk_1
		foreign key (page_seq_no) references page (seq_no)
);

create index fk_reject_number_page
	on msg_reject_number (page_seq_no);

create table if not exists msgbox
(
	member_seq_no bigint not null,
	msg_seq_no bigint not null,
	app_type varchar(32) default 'user' not null,
	primary key (member_seq_no, msg_seq_no),
	constraint msgbox_ibfk_1
		foreign key (member_seq_no) references member (seq_no),
	constraint msgbox_ibfk_2
		foreign key (msg_seq_no) references msg (seq_no)
);

create index fk_msgbox_msg
	on msgbox (msg_seq_no);

create table if not exists offer_deny_page
(
	offer_seq_no bigint not null,
	page_seq_no bigint not null,
	primary key (offer_seq_no, page_seq_no),
	constraint fk_offer_deny_page1
		foreign key (offer_seq_no) references offer (seq_no),
	constraint fk_offer_deny_page2
		foreign key (page_seq_no) references page (seq_no)
);

create index fk_offer_deny_page1_idx
	on offer_deny_page (offer_seq_no);

create index fk_offer_deny_page2_idx
	on offer_deny_page (page_seq_no);

create index delivery_radius
	on page (delivery_radius);

create index fk_page_agent
	on page (agent_seq_no);

create index fk_page_background_idx
	on page (bg_seq_no);

create index fk_page_cooperation_idx
	on page (coop_seq_no);

create index fk_page_member_idx
	on page (member_seq_no);

create index fk_page_profile_idx
	on page (profile_seq_no);

create index is_chain
	on page (is_chain);

create index is_delivery
	on page (is_delivery);

create index is_delivery_orderable
	on page (is_delivery_orderable);

create index is_packing_orderable
	on page (is_packing_orderable);

create index is_seller
	on page (is_seller);

create index is_shop_orderable
	on page (is_shop_orderable);

create index latitude
	on page (latitude, longitude);

create index main_goods_seq_no
	on page (main_goods_seq_no);

create index parent_seq_no
	on page (parent_seq_no);

create index thema_seq_no
	on page (thema_seq_no);

create table if not exists page_board
(
	page_seq_no bigint not null
		primary key,
	pr_seq_no bigint null,
	review_seq_no bigint null,
	constraint fk_page_board_page
		foreign key (page_seq_no) references page (seq_no),
	constraint fk_page_pr_board
		foreign key (pr_seq_no) references board (seq_no)
			on update set null on delete set null,
	constraint fk_page_review_board
		foreign key (review_seq_no) references board (seq_no)
			on update set null on delete set null
)
charset=utf8;

create index fk_page_pr_board_idx
	on page_board (pr_seq_no);

create index fk_page_review_board_idx
	on page_board (review_seq_no);

create table if not exists page_closed
(
	seq_no bigint auto_increment comment 'page(상점) 영업시간 순번'
		primary key,
	page_seq_no bigint not null comment 'page(상점) 순번',
	every_week int default 0 not null comment '0: 매주, 1: 첫번째 주, 2: 두번째 주, 3: 세번째 주, 4: 네번째 주, 5: 다섯번째 주',
	week_day enum('mon', 'tue', 'wed', 'thu', 'fri', 'sat', 'sun') not null comment '쉬는 요일',
	constraint page_seq_no
		unique (page_seq_no, every_week, week_day),
	constraint page_closed_ibfk_1
		foreign key (page_seq_no) references page (seq_no)
			on delete cascade
);

create table if not exists page_coupon_template
(
	page_seq_no bigint not null,
	template_seq_no bigint not null,
	allocate_prop varchar(4096) null,
	primary key (page_seq_no, template_seq_no),
	constraint fk_coupon_tempate_page
		foreign key (template_seq_no) references coupon_template (seq_no),
	constraint fk_page_coupon_template
		foreign key (page_seq_no) references page (seq_no)
);

create index fk_coupon_tempate_page_idx
	on page_coupon_template (template_seq_no);

create index fk_page_coupon_template_idx
	on page_coupon_template (page_seq_no);

create table if not exists page_goods_category
(
	seq_no bigint auto_increment comment '상점별 상품 카테고리 순번'
		primary key,
	page_seq_no bigint not null comment '상점 순번',
	goods_category_seq_no bigint not null comment '상품 카테고리 순번',
	constraint page_seq_no
		unique (page_seq_no, goods_category_seq_no)
);

create table if not exists page_intro_image
(
	page_seq_no bigint not null,
	priority tinyint default 1 not null,
	reg_datetime datetime default (CURRENT_TIMESTAMP) not null,
	attach_seq_no bigint not null,
	primary key (page_seq_no, attach_seq_no),
	constraint fk_page_intro_attach
		foreign key (attach_seq_no) references attachment (seq_no),
	constraint fk_page_intro_page
		foreign key (page_seq_no) references page (seq_no)
);

create index fk_page_intro_attach_idx
	on page_intro_image (attach_seq_no);

create index fk_page_intro_page_idx
	on page_intro_image (page_seq_no);

create table if not exists page_intro_movie
(
	page_seq_no bigint not null,
	seq_no smallint not null,
	url varchar(1024) not null,
	primary key (page_seq_no, seq_no),
	constraint page_intro_movie_ibfk_1
		foreign key (page_seq_no) references page (seq_no)
);

create table if not exists page_opentime
(
	seq_no bigint auto_increment comment 'page(상점) 영업시간 순번'
		primary key,
	page_seq_no bigint not null comment 'page(상점) 순번',
	type int default 0 not null comment '0:주중, 1:주말, 2:요일별',
	week_day enum('mon', 'tue', 'wed', 'thu', 'fri', 'sat', 'sun') null comment '영업 요일',
	start_time time null comment '영업 시작 시각',
	end_time time null comment '영업 종료 시각',
	next_day tinyint(1) default 0 not null comment '영업 종료 시각 다음날 새벽까지 유무',
	constraint page_seq_no
		unique (page_seq_no, type, week_day),
	constraint page_opentime_ibfk_1
		foreign key (page_seq_no) references page (seq_no)
			on delete cascade
);

create table if not exists page_seller
(
	member_seq_no bigint auto_increment comment '사용자 순번',
	page_seq_no bigint not null comment '품 페이지 순번'
		primary key,
	biz_email varchar(255) not null comment '상점주 email',
	biz_bank_code varchar(32) null comment '상점주 은행명(코드관리)',
	biz_bank_book_no varchar(64) null comment '상점주 통장 번호',
	biz_bank_book_owner varchar(32) null comment '상점주 통장 예금주명',
	biz_name varchar(64) null comment '상점 상호명',
	biz_owner varchar(32) null comment '상점 대표자명',
	biz_reg_no char(12) null comment '상점 사업자등록번호',
	biz_address varchar(255) null comment '상점 소재지',
	biz_type varchar(64) null comment '상점 업태',
	biz_category varchar(255) null comment '상점 종목',
	biz_prop varchar(1024) null comment '상점 속성(reserved)',
	biz_cancel_msg varchar(1024) null comment '상점 인증 취소/중단 사유',
	biz_pay_ratio float default 5 not null comment '상점 판매 수수료율',
	is_seller tinyint(1) default 0 not null comment '0 : 판매자 미인증, 1: 판매자 인증완료',
	is_terms_accept tinyint(1) default 0 not null comment '0 : 약관 미동의, 1: 약관 동의',
	reg_datetime datetime not null comment '신청시각',
	mod_datetime datetime not null comment '수정/처리 시각',
	status int default 0 not null comment '0: 승인대기, 1: 승인, 2: 반려, 3: 재요청, 4: 중지',
	origin_desc varchar(512) null comment '상점 상품 원산지 표기',
	constraint biz_reg_no
		unique (biz_reg_no),
	constraint page_seller_ibfk_1
		foreign key (page_seq_no) references page (seq_no)
			on delete cascade,
	constraint page_seller_ibfk_2
		foreign key (member_seq_no) references member (seq_no)
);

create index member_seq_no
	on page_seller (member_seq_no);

create table if not exists page_vocation
(
	seq_no bigint auto_increment comment 'page(상점) 영업시간 순번'
		primary key,
	page_seq_no bigint not null comment 'page(상점) 순번',
	start_date date not null comment '휴일 시작일',
	end_date date not null comment '휴일 종료일',
	constraint page_vocation_ibfk_1
		foreign key (page_seq_no) references page (seq_no)
			on delete cascade
);

create index page_seq_no
	on page_vocation (page_seq_no);

create table if not exists pagegroup
(
	seq_no bigint auto_increment
		primary key,
	name varchar(64) null,
	status varchar(32) default 'active' not null,
	platform varchar(32) null,
	page_count int default 0 not null,
	priority int null,
	group_prop varchar(4096) null,
	reg_datetime datetime default (CURRENT_TIMESTAMP) not null,
	registrant_seq_no bigint not null
);

create table if not exists page_group
(
	group_seq_no bigint not null,
	page_seq_no bigint not null,
	priority int null,
	primary key (group_seq_no, page_seq_no),
	constraint fk_page_group_group
		foreign key (group_seq_no) references pagegroup (seq_no),
	constraint fk_page_group_page
		foreign key (page_seq_no) references page (seq_no)
)
charset=utf8;

create index fk_page_group_group_idx
	on page_group (page_seq_no);

create index fk_page_group_page_idx
	on page_group (page_seq_no);

create table if not exists plus
(
	seq_no bigint auto_increment
		primary key,
	block enum('Y', 'N') default 'N' not null,
	member_seq_no bigint not null,
	page_seq_no bigint not null,
	reg_datetime datetime default (CURRENT_TIMESTAMP) not null,
	constraint plus_ibfk_1
		foreign key (member_seq_no) references member (seq_no),
	constraint plus_ibfk_2
		foreign key (page_seq_no) references page (seq_no)
);

create table if not exists fan_group
(
	group_seq_no bigint not null,
	fan_seq_no bigint not null,
	primary key (group_seq_no, fan_seq_no),
	constraint fan_group_ibfk_1
		foreign key (group_seq_no) references fangroup (seq_no),
	constraint fan_group_ibfk_2
		foreign key (fan_seq_no) references plus (seq_no)
);

create index fk_fan_group_2
	on fan_group (fan_seq_no);

create index fk_plus_member
	on plus (member_seq_no);

create index fk_plus_page
	on plus (page_seq_no);

create table if not exists plusgroup
(
	seq_no bigint auto_increment
		primary key,
	name varchar(128) not null,
	sort_num int default 1 not null,
	reg_datetime datetime default (CURRENT_TIMESTAMP) not null,
	mod_datetime datetime null,
	member_seq_no bigint null,
	default_group enum('Y', 'N') default 'N' not null,
	group_type varchar(32) default 'user' not null,
	constraint plusgroup_ibfk_1
		foreign key (member_seq_no) references member (seq_no)
);

create table if not exists plus_group
(
	group_seq_no bigint not null,
	plus_seq_no bigint not null,
	primary key (group_seq_no, plus_seq_no),
	constraint plus_group_ibfk_1
		foreign key (group_seq_no) references plusgroup (seq_no),
	constraint plus_group_ibfk_2
		foreign key (plus_seq_no) references plus (seq_no)
);

create index fk_plus_group_2
	on plus_group (plus_seq_no);

create index fk_plusgroup_member
	on plusgroup (member_seq_no);

create table if not exists pos_agent
(
	seq_no bigint auto_increment comment 'POS 대행사 순번'
		primary key,
	agent varchar(64) not null comment 'POS 업체 Agent 명',
	access_token varchar(64) not null comment 'POS Agent Api 토큰'
);

create table if not exists delivery_page
(
	seq_no bigint auto_increment comment '매장(shop) 순번'
		primary key,
	agent_seq_no bigint not null comment 'POS 대행사 순번',
	id varchar(64) not null comment '상점 고유 아이디',
	name varchar(128) null comment '상점명',
	constraint agent_seq_no
		unique (agent_seq_no, id),
	constraint delivery_page_ibfk_1
		foreign key (agent_seq_no) references pos_agent (seq_no)
			on delete cascade
);

create table if not exists private_key
(
	uuid varchar(64) not null comment 'UUID Primary Key'
		primary key,
	private_key blob not null comment 'PrivateKey Object Data',
	reg_datetime datetime not null
);

create table if not exists product
(
	seq_no bigint auto_increment
		primary key,
	product_title varchar(256) not null,
	note varchar(8192) null,
	supply_price decimal(15,4) null,
	sales_price decimal(15,4) null,
	main_image_seq_no bigint not null,
	product_prop varchar(8192) null,
	start_datetime datetime not null,
	end_datetime datetime not null,
	status varchar(32) default 'active' not null,
	reg_datetime datetime default (CURRENT_TIMESTAMP) not null,
	constraint fk_product_attachment
		foreign key (main_image_seq_no) references attachment (seq_no)
)
charset=utf8;

create table if not exists coop_product
(
	coop_seq_no bigint not null,
	product_seq_no bigint not null,
	primary key (coop_seq_no, product_seq_no),
	constraint fk_coop_product1
		foreign key (coop_seq_no) references cooperation (seq_no),
	constraint fk_coop_product2
		foreign key (product_seq_no) references product (seq_no)
);

create index fk_coop_product1_idx
	on coop_product (coop_seq_no);

create index fk_coop_product2_idx
	on coop_product (product_seq_no);

create table if not exists coop_product_config
(
	coop_seq_no bigint not null,
	product_seq_no bigint not null,
	page_seq_no bigint not null,
	display enum('Y', 'N') default 'Y' not null,
	sales_price decimal(15,4) null,
	config_prop varchar(2048) null,
	primary key (coop_seq_no, product_seq_no, page_seq_no),
	constraint fk_coop_product_config1
		foreign key (coop_seq_no, product_seq_no) references coop_product (coop_seq_no, product_seq_no),
	constraint fk_coop_product_config2
		foreign key (page_seq_no) references page (seq_no)
);

create index R_ge_op__ce7
	on coop_product_config (page_seq_no);

create table if not exists page_product
(
	page_seq_no bigint not null,
	product_seq_no bigint not null,
	primary key (page_seq_no, product_seq_no),
	constraint fk_page_product
		foreign key (page_seq_no) references page (seq_no),
	constraint fk_product_page
		foreign key (product_seq_no) references product (seq_no)
);

create index fk_page_product_idx
	on page_product (page_seq_no);

create index fk_product_page_idx
	on page_product (product_seq_no);

create index fk_product_attachment_idx
	on product (main_image_seq_no);

create table if not exists product_category
(
	seq_no bigint auto_increment
		primary key,
	name varchar(64) not null,
	priority int default 1 not null,
	category_prop varchar(2048) null,
	reg_datetime datetime default (CURRENT_TIMESTAMP) not null
);

create table if not exists coop_product_category
(
	coop_seq_no bigint not null,
	category_seq_no bigint not null,
	primary key (coop_seq_no, category_seq_no),
	constraint fk_coop_prod_category1
		foreign key (coop_seq_no) references cooperation (seq_no),
	constraint fk_coop_prod_category2
		foreign key (category_seq_no) references product_category (seq_no)
);

create index fk_coop_prod_category1_idx
	on coop_product_category (coop_seq_no);

create index fk_coop_prod_category2_idx
	on coop_product_category (category_seq_no);

create table if not exists coop_product_dp
(
	coop_seq_no bigint not null,
	category_seq_no bigint not null,
	product_seq_no bigint not null,
	primary key (coop_seq_no, category_seq_no, product_seq_no),
	constraint fk_coop_product_dp1
		foreign key (coop_seq_no, category_seq_no) references coop_product_category (coop_seq_no, category_seq_no),
	constraint fk_coop_product_dp2
		foreign key (coop_seq_no, product_seq_no) references coop_product (coop_seq_no, product_seq_no)
);

create index fk_coop_product_dp1_idx
	on coop_product_dp (coop_seq_no, category_seq_no);

create index fk_coop_product_dp2_idx
	on coop_product_dp (coop_seq_no, product_seq_no);

create table if not exists page_product_category
(
	page_seq_no bigint not null,
	category_seq_no bigint not null,
	primary key (page_seq_no, category_seq_no),
	constraint fk_page_prod_category
		foreign key (page_seq_no) references page (seq_no),
	constraint fk_prod_category_page
		foreign key (category_seq_no) references product_category (seq_no)
);

create index fk_page_prod_category_idx
	on page_product_category (page_seq_no);

create index fk_prod_category_page_idx
	on page_product_category (category_seq_no);

create table if not exists page_product_dp
(
	page_seq_no bigint not null,
	category_seq_no bigint not null,
	product_seq_no bigint not null,
	primary key (page_seq_no, category_seq_no, product_seq_no),
	constraint fk_page_product_dp1
		foreign key (page_seq_no, category_seq_no) references page_product_category (page_seq_no, category_seq_no),
	constraint fk_page_product_dp2
		foreign key (page_seq_no, product_seq_no) references page_product (page_seq_no, product_seq_no)
);

create index fk_page_product_dp1_idx
	on page_product_dp (page_seq_no, category_seq_no);

create index fk_page_product_dp2_idx
	on page_product_dp (page_seq_no, product_seq_no);

create table if not exists promotion
(
	seq_no bigint auto_increment
		primary key,
	title varchar(256) not null,
	image_seq_no bigint null,
	type varchar(32) default 'none' not null,
	start_datetime datetime not null,
	end_datetime datetime not null,
	status varchar(32) default 'active' not null,
	contents longtext null,
	win_prop mediumtext null,
	lose_prop mediumtext null,
	join_bol int default 0 not null,
	limit_type varchar(32) default 'daily' not null,
	limit_count int default 1 not null,
	promotion_prop varchar(4096) null,
	reg_datetime datetime default (CURRENT_TIMESTAMP) not null,
	registrant_seq_no bigint not null,
	constraint promotion_ibfk_1
		foreign key (image_seq_no) references attachment (seq_no)
);

create index fk_promotion_attachment
	on promotion (image_seq_no);

create table if not exists promotion_lots_config
(
	seq_no bigint auto_increment
		primary key,
	promotion_seq_no bigint not null,
	title varchar(256) not null,
	probability smallint not null,
	limit_count int default 0 not null,
	win_count int default 0 not null,
	image_seq_no bigint null,
	constraint promotion_lots_config_ibfk_1
		foreign key (promotion_seq_no) references promotion (seq_no),
	constraint promotion_lots_config_ibfk_2
		foreign key (image_seq_no) references attachment (seq_no)
);

create table if not exists promotion_join
(
	seq_no bigint auto_increment
		primary key,
	promotion_seq_no bigint not null,
	member_seq_no bigint not null,
	reg_datetime datetime default (CURRENT_TIMESTAMP) not null,
	join_result varchar(32) not null,
	impression mediumtext null,
	status varchar(32) null,
	lots_seq_no bigint null,
	constraint promotion_join_ibfk_1
		foreign key (lots_seq_no) references promotion_lots_config (seq_no),
	constraint promotion_join_ibfk_2
		foreign key (promotion_seq_no) references promotion (seq_no),
	constraint promotion_join_ibfk_3
		foreign key (member_seq_no) references member (seq_no)
);

create index fk_join_log
	on promotion_join (lots_seq_no);

create index fk_join_member
	on promotion_join (member_seq_no);

create index fk_join_promotion
	on promotion_join (promotion_seq_no);

create index fk_lots_attachment
	on promotion_lots_config (image_seq_no);

create index fk_lots_promotion
	on promotion_lots_config (promotion_seq_no);

create table if not exists promotion_page
(
	promotion_seq_no bigint not null,
	page_seq_no bigint not null,
	primary key (promotion_seq_no, page_seq_no),
	constraint promotion_page_ibfk_1
		foreign key (promotion_seq_no) references promotion (seq_no),
	constraint promotion_page_ibfk_2
		foreign key (page_seq_no) references page (seq_no)
);

create index fk_promotion_page_2
	on promotion_page (page_seq_no);

create table if not exists push_target
(
	msg_seq_no bigint not null,
	member_seq_no bigint not null,
	confirm_prop varchar(4096) null,
	status varchar(32) default 'ready' null,
	readed enum('Y', 'N') default 'N' not null,
	send_datetime datetime null,
	primary key (msg_seq_no, member_seq_no),
	constraint fk_push_target_member
		foreign key (member_seq_no) references member (seq_no),
	constraint fk_push_target_msg
		foreign key (msg_seq_no) references msg (seq_no)
);

create index fk_push_target_member_idx
	on push_target (member_seq_no);

create index fk_push_target_msg_idx
	on push_target (msg_seq_no);

create table if not exists recv_note
(
	member_seq_no bigint not null,
	note_seq_no bigint not null,
	readed enum('Y', 'N') default 'N' not null,
	read_datetime datetime null,
	reply_seq_no bigint null,
	primary key (member_seq_no, note_seq_no),
	constraint recv_note_ibfk_1
		foreign key (member_seq_no) references member (seq_no),
	constraint recv_note_ibfk_2
		foreign key (note_seq_no) references note (seq_no),
	constraint recv_note_ibfk_3
		foreign key (reply_seq_no) references note (seq_no)
)
charset=utf8;

create index fk_recv_note_note
	on recv_note (note_seq_no);

create index fk_recv_note_reply
	on recv_note (reply_seq_no);

create table if not exists report
(
	seq_no bigint auto_increment
		primary key,
	member_seq_no bigint null,
	page_seq_no bigint null,
	article_seq_no bigint null,
	comment_seq_no bigint null,
	target_type varchar(32) not null,
	reason varchar(32) not null,
	reg_datetime datetime default (CURRENT_TIMESTAMP) not null,
	admin_seq_no bigint null,
	goods_seq_no bigint null,
	goods_review_seq_no bigint null,
	constraint report_ibfk_1
		foreign key (member_seq_no) references member (seq_no),
	constraint report_ibfk_2
		foreign key (page_seq_no) references page (seq_no),
	constraint report_ibfk_3
		foreign key (article_seq_no) references article (seq_no),
	constraint report_ibfk_4
		foreign key (comment_seq_no) references comment (seq_no),
	constraint report_ibfk_5
		foreign key (admin_seq_no) references admin (seq_no),
	constraint report_ibfk_6
		foreign key (goods_seq_no) references goods (seq_no),
	constraint report_ibfk_7
		foreign key (goods_review_seq_no) references goods_review (seq_no)
);

create index fk_report_admin
	on report (admin_seq_no);

create index fk_report_article
	on report (article_seq_no);

create index fk_report_comment
	on report (comment_seq_no);

create index fk_report_member
	on report (member_seq_no);

create index fk_report_page
	on report (page_seq_no);

create index goods_review_seq_no
	on report (goods_review_seq_no);

create index goods_seq_no
	on report (goods_seq_no);

create table if not exists request
(
	seq_no bigint auto_increment comment 'http api 요청 순번'
		primary key,
	member_seq_no bigint not null comment '요청 사용자 순번',
	uri varchar(255) not null comment '요청 uri',
	request_key varchar(32) not null comment '요청 unique key',
	rescode int default 200 not null comment 'http 응답 code 값',
	response text null comment 'http 응답 body',
	reg_datetime datetime not null comment '등록 시각',
	constraint request_key
		unique (request_key),
	constraint request_ibfk_1
		foreign key (member_seq_no) references member (seq_no)
);

create index member_seq_no
	on request (member_seq_no);

create index rescode
	on request (rescode, reg_datetime);

create table if not exists request_log
(
	seq_no bigint auto_increment comment 'request 순번'
		primary key,
	member_seq_no bigint null comment 'request session 사용자 순번',
	is_exception tinyint(1) default 0 null comment 'exception 여부',
	http_status int null comment 'HTTP Status code',
	result_code int null comment 'Exception return code',
	request_id varchar(64) null comment 'HTTP Request Id',
	uri varchar(255) null comment 'HTTP Request uri',
	response varchar(8192) null comment 'exception 오류 메세지',
	reg_datetime datetime not null comment '등록시각',
	ip varchar(32) null comment 'client ip',
	server varchar(64) null comment 'server: local, stage, prod1, prod2'
);

create index ip
	on request_log (ip);

create index is_exception
	on request_log (is_exception);

create index member_seq_no
	on request_log (member_seq_no);

create index request_id
	on request_log (request_id);

create index result_code
	on request_log (result_code);

create index server
	on request_log (server);

create index uri
	on request_log (uri);

create table if not exists saved_msg
(
	member_seq_no bigint not null,
	seq_no int not null,
	save_prop varchar(8192) null,
	priority int default 1 not null,
	reg_datetime datetime default (CURRENT_TIMESTAMP) not null,
	primary key (member_seq_no, seq_no),
	constraint saved_msg_ibfk_1
		foreign key (member_seq_no) references member (seq_no)
);

create table if not exists search_keyword
(
	keyword varchar(64) not null
		primary key,
	ad_price int default 0 not null,
	status varchar(32) not null,
	reg_datetime datetime default (CURRENT_TIMESTAMP) not null,
	registrant_seq_no char(18) not null
)
charset=utf8;

create table if not exists page_keyword
(
	keyword varchar(64) not null,
	page_seq_no bigint not null,
	end_datetime datetime not null,
	start_datetime datetime not null,
	status varchar(32) default 'normal' not null,
	visit_count bigint default 0 not null,
	primary key (keyword, page_seq_no),
	constraint fk_page_keyword1
		foreign key (keyword) references search_keyword (keyword),
	constraint fk_page_keyword2
		foreign key (page_seq_no) references page (seq_no)
)
charset=utf8;

create index fk_page_keyword1_idx
	on page_keyword (keyword);

create index fk_page_keyword2_idx
	on page_keyword (page_seq_no);

create table if not exists send_mobilegift
(
	seq_no bigint auto_increment
		primary key,
	purchase_seq_no bigint not null,
	mobile_number varchar(64) not null,
	name varchar(64) null,
	send_status varchar(32) default 'insert' not null,
	confirm_key varchar(64) null,
	result_msg varchar(1024) null,
	send_prop varchar(2048) null,
	reg_datetime datetime default (CURRENT_TIMESTAMP) not null,
	constraint send_mobilegift_ibfk_1
		foreign key (purchase_seq_no) references mobilegift_purchase (seq_no)
);

create index R_bilegif_nd__f69
	on send_mobilegift (purchase_seq_no);

create table if not exists send_mobilegift_history
(
	seq_no int not null,
	send_seq_no bigint not null,
	prev_status varchar(32) null,
	proc_status varchar(32) null,
	result_msg varchar(1024) null,
	history_prop varchar(1024) null,
	reg_datetime datetime default (CURRENT_TIMESTAMP) not null,
	primary key (send_seq_no, seq_no),
	constraint send_mobilegift_history_ibfk_1
		foreign key (send_seq_no) references send_mobilegift (seq_no)
)
charset=utf8;

create table if not exists send_note
(
	member_seq_no bigint not null,
	note_seq_no bigint not null,
	receiver_count int default 1 not null,
	receiver_seq_no bigint null,
	origin_seq_no bigint null,
	primary key (member_seq_no, note_seq_no),
	constraint send_note_ibfk_1
		foreign key (member_seq_no) references member (seq_no),
	constraint send_note_ibfk_2
		foreign key (note_seq_no) references note (seq_no),
	constraint send_note_ibfk_3
		foreign key (receiver_seq_no) references member (seq_no),
	constraint send_note_ibfk_4
		foreign key (origin_seq_no) references note (seq_no)
);

create index fk_send_note_note
	on send_note (note_seq_no);

create index fk_send_note_origin
	on send_note (origin_seq_no);

create index fk_send_note_receiver
	on send_note (receiver_seq_no);

create table if not exists short_url
(
	seq_no bigint auto_increment comment 'shortURL 순번'
		primary key,
	member_seq_no bigint null comment 'shortURL 생성 사용자 순번',
	target_type varchar(32) not null comment 'shortURL 매핑 타겟 : goods, page, event',
	target_seq_no bigint not null comment 'shortURL 매핑 타겟 순번',
	id varchar(32) not null comment 'shortURL unique key',
	real_url varchar(255) not null comment '실제 URL',
	reg_datetime datetime not null comment '등록시각',
	mod_datetime datetime not null comment '변시각',
	expire_datetime datetime null comment 'expire 시각',
	status int default 1 not null comment '0:disable, 1:enable',
	phase varchar(8) default 'stage' not null comment '서버 phase : LOCAL, DEV, STAGE, PROD',
	click_count bigint default 0 not null comment 'shortURL 클릭 카운트 수',
	buy_count bigint default 0 not null comment 'shorURL 을 통한 실제 구매 카운트 수',
	reward_bol bigint null comment '리워드 bol',
	prop varchar(1024) null comment '확장 옵션',
	constraint id
		unique (id)
);

create index member_seq_no
	on short_url (member_seq_no);

create index phase
	on short_url (phase);

create index status
	on short_url (status);

create index target_seq_no
	on short_url (target_seq_no);

create index target_type
	on short_url (target_type);

create table if not exists short_url_event
(
	seq_no bigint auto_increment comment 'shortURL 순번'
		primary key,
	short_url_seq_no bigint null comment 'shortURL 생성 순번',
	remote_address varchar(255) null comment '접속자 address',
	remote_user_agent varchar(255) null comment '접속자 User-Agent 헤더 정보',
	type varchar(8) default 'click' not null comment 'click, buy, etc',
	reg_datetime datetime not null comment '등록시각'
);

create index short_url_seq_no
	on short_url_event (short_url_seq_no);

create table if not exists sms_msg
(
	seq_no bigint auto_increment
		primary key,
	member_seq_no bigint not null,
	page_seq_no bigint null,
	sms_type varchar(8) default 'lms' not null,
	msg_type varchar(32) not null,
	reserve_datetime datetime null,
	status varchar(32) not null,
	subject varchar(128) null,
	contents varchar(8192) null,
	target_count int default 0 not null,
	succ_count int default 0 not null,
	fail_count int default 0 not null,
	remain_count int not null,
	reserved enum('Y', 'N') default 'N' not null,
	msg_prop varchar(4096) null,
	reg_datetime datetime default (CURRENT_TIMESTAMP) not null,
	constraint fk_sms_msg_member
		foreign key (member_seq_no) references member (seq_no),
	constraint fk_sms_msg_page
		foreign key (page_seq_no) references page (seq_no)
);

create index fk_sms_msg_member_idx
	on sms_msg (member_seq_no);

create index fk_sms_msg_page_idx
	on sms_msg (page_seq_no);

create table if not exists sms_msg_target
(
	seq_no bigint not null,
	mobile_number varchar(64) not null,
	confirm_key varchar(64) null,
	primary key (seq_no, mobile_number),
	constraint fk_sms_msg_target
		foreign key (seq_no) references sms_msg (seq_no)
);

create table if not exists sms_target
(
	msg_seq_no bigint not null,
	mobile_number varchar(64) not null,
	name varchar(64) null,
	confirm_prop varchar(4096) null,
	status varchar(32) default 'ready' not null,
	send_datetime datetime null,
	primary key (msg_seq_no, mobile_number),
	constraint fk_sms_target_msg
		foreign key (msg_seq_no) references msg (seq_no)
);

create table if not exists sns_link
(
	page_seq_no bigint not null,
	seq_no int not null,
	sns_type varchar(32) null,
	linkage enum('Y', 'N') default 'N' not null,
	url varchar(512) null,
	reg_datetime datetime default (CURRENT_TIMESTAMP) not null,
	mod_datetime datetime null,
	primary key (page_seq_no, seq_no),
	constraint fk_sns_link_page
		foreign key (page_seq_no) references page (seq_no)
);

create table if not exists system_template_code
(
	code varchar(7) not null
		primary key,
	depth int not null,
	name varchar(100) null
)
charset=utf8;

create table if not exists system_template
(
	code varchar(20) not null
		primary key,
	system_template_code varchar(7) not null,
	name varchar(100) not null,
	subject varchar(200) not null,
	contents longtext not null,
	reg_datetime datetime default (CURRENT_TIMESTAMP) not null,
	registrant_seq_no bigint not null,
	mod_datetime datetime null,
	modifier_seq_no bigint null,
	constraint fk_system_template_code1
		foreign key (system_template_code) references system_template_code (code)
)
charset=utf8;

create index fk_system_template_system_temple_code1_idx
	on system_template (system_template_code);

create table if not exists tbl_submit_queue
(
	CMP_MSG_ID int unsigned auto_increment
		primary key,
	CMP_MSG_GROUP_ID varchar(20) null,
	USR_ID varchar(16) not null,
	SMS_GB char default '1' null,
	USED_CD char(2) not null,
	RESERVED_FG char not null,
	RESERVED_DTTM char(14) not null,
	SAVED_FG char default '0' null,
	RCV_PHN_ID varchar(24) not null,
	SND_PHN_ID varchar(24) null,
	NAT_CD varchar(8) null,
	ASSIGN_CD varchar(5) default '00000' null,
	SND_MSG varchar(2000) null,
	CALLBACK_URL varchar(120) null,
	CONTENT_CNT int default 0 null,
	CONTENT_MIME_TYPE varchar(128) null,
	CONTENT_PATH varchar(1024) null,
	CMP_SND_DTTM char(14) null,
	CMP_RCV_DTTM char(14) null,
	REG_SND_DTTM char(14) null,
	REG_RCV_DTTM char(14) null,
	MACHINE_ID char(2) null,
	SMS_STATUS char default '0' null,
	RSLT_VAL char(4) null,
	MSG_TITLE varchar(200) null,
	TELCO_ID char(4) null,
	ETC_CHAR_1 varchar(100) null,
	ETC_CHAR_2 varchar(100) null,
	ETC_CHAR_3 varchar(100) null,
	ETC_CHAR_4 varchar(100) null,
	ETC_INT_5 int null,
	ETC_INT_6 int null
);

create index IDX_SUBMIT_QUEUE_1
	on tbl_submit_queue (SMS_STATUS, RESERVED_FG);

create index IDX_SUBMIT_QUEUE_2
	on tbl_submit_queue (CMP_MSG_ID, SMS_STATUS);

create table if not exists terms
(
	seq_no bigint auto_increment
		primary key,
	code varchar(32) not null,
	status varchar(32) default 'active' not null,
	subject varchar(256) not null,
	compulsory enum('Y', 'N') default 'N' not null,
	name varchar(128) not null,
	contents longtext not null,
	reg_datetime datetime default (CURRENT_TIMESTAMP) not null,
	registrant_seq_no bigint not null,
	mod_datetime datetime null,
	modifier_seq_no bigint null,
	type varchar(8) default 'signup' not null comment '약관종류:signup, seller, buy'
);

create table if not exists app_terms
(
	app_key varchar(64) not null,
	terms_seq_no bigint not null,
	primary key (app_key, terms_seq_no),
	constraint fk_app_terms_app
		foreign key (app_key) references app (app_key),
	constraint fk_app_terms_terms
		foreign key (terms_seq_no) references terms (seq_no)
)
charset=utf8;

create index fk_app_terms_terms_idx
	on app_terms (terms_seq_no);

create table if not exists terms_sign
(
	member_seq_no bigint not null,
	terms_seq_no bigint not null,
	reg_datetime datetime default (CURRENT_TIMESTAMP) not null,
	primary key (member_seq_no, terms_seq_no),
	constraint fk_sign_member
		foreign key (member_seq_no) references member (seq_no),
	constraint fk_sign_terms
		foreign key (terms_seq_no) references terms (seq_no)
);

create index fk_sign_member_idx
	on terms_sign (member_seq_no);

create index fk_sign_terms_idx
	on terms_sign (terms_seq_no);

create table if not exists test
(
	fff varchar(1024) null
)
charset=utf8;

create table if not exists test_category
(
	idx int unsigned auto_increment comment '고유값'
		primary key,
	parentIdx int unsigned not null comment '부모고유값',
	title varchar(100) not null comment '제목'
)
comment '카테고리 테이블';

create table if not exists test_week
(
	seq_no int unsigned auto_increment
		primary key,
	start date not null,
	end date not null
);

create index idx_satrt_end
	on test_week (start, end);

create table if not exists theme
(
	seq_no bigint auto_increment
		primary key,
	name varchar(128) not null,
	sort_num int default 1 not null,
	status varchar(32) default 'active' not null,
	click_count bigint default 0 not null,
	reg_datetime datetime default (CURRENT_TIMESTAMP) not null,
	registrant_seq_no bigint not null,
	mod_datetime datetime null,
	modifier_seq_no bigint null
)
charset=utf8;

create table if not exists theme_page
(
	theme_seq_no bigint not null,
	page_seq_no bigint not null,
	reg_datetime datetime default (CURRENT_TIMESTAMP) not null,
	registrant_seq_no bigint not null,
	primary key (theme_seq_no, page_seq_no),
	constraint fk_page_theme
		foreign key (page_seq_no) references page (seq_no),
	constraint fk_theme_page
		foreign key (theme_seq_no) references theme (seq_no)
);

create index fk_page_theme_idx
	on theme_page (page_seq_no);

create index fk_theme_page_idx
	on theme_page (theme_seq_no);

create table if not exists token
(
	seq_no bigint auto_increment comment 'oauth2 토큰 순번'
		primary key,
	type varchar(64) not null comment 'client type',
	client varchar(64) not null comment 'oauth2 client-id',
	client_secret varchar(255) not null comment 'oauth2 client-secret',
	token varchar(255) not null comment 'oauth2 token',
	refresh_token varchar(255) not null comment 'oauth2 refresh-token',
	expire_date datetime not null comment 'oauth2 token expire-date',
	refresh_expire_date datetime not null comment 'oauth2 refresh-token expire-date',
	scope varchar(64) default 'readWrite' not null comment 'oauth2 인증 허가 범위',
	token_type varchar(64) default 'Bearer' null comment 'oauth2 토큰 타입',
	api_host_url varchar(255) not null comment 'api host url',
	search_host_url varchar(255) not null comment 'search host url',
	base_uri varchar(64) default '/store/api' not null comment 'api host base-uri',
	constraint client
		unique (client, client_secret),
	constraint token
		unique (token)
);

create index type
	on token (type);

create table if not exists tr_test
(
	seq_no bigint auto_increment
		primary key,
	reg_datetime datetime default (CURRENT_TIMESTAMP) not null
);

create table if not exists tr_test_sub
(
	seq_no bigint not null
		primary key,
	sub_name varchar(45) not null comment '글',
	constraint fk_tr_test_sub
		foreign key (seq_no) references tr_test (seq_no)
			on update cascade on delete cascade
);

create index fk_tr_test_sub_idx1
	on tr_test_sub (seq_no);

create table if not exists use_advertise
(
	use_datetime datetime default (CURRENT_TIMESTAMP) not null,
	member_seq_no bigint not null,
	ad_seq_no bigint not null,
	seq_no bigint auto_increment
		primary key,
	constraint use_advertise_ibfk_1
		foreign key (member_seq_no) references member (seq_no),
	constraint use_advertise_ibfk_2
		foreign key (ad_seq_no) references advertise (seq_no)
);

create index fk_use_ad_ad
	on use_advertise (ad_seq_no);

create index fk_use_ad_member
	on use_advertise (member_seq_no);

create table if not exists user_request
(
	seq_no bigint auto_increment
		primary key,
	req_type varchar(32) not null,
	req_prop varchar(4096) null,
	status varchar(32) not null,
	member_seq_no bigint null,
	constraint user_request_ibfk_1
		foreign key (member_seq_no) references member (seq_no)
);

create index fk_user_req_member
	on user_request (member_seq_no);

create table if not exists user_request_proc
(
	req_seq_no bigint not null,
	seq_no int not null,
	prev_status varchar(32) null,
	proc_status varchar(32) not null,
	proc_note varchar(1024) null,
	registrant_seq_no bigint not null,
	reg_datetime datetime default (CURRENT_TIMESTAMP) not null,
	primary key (req_seq_no, seq_no),
	constraint user_request_proc_ibfk_1
		foreign key (req_seq_no) references user_request (seq_no)
);

create table if not exists verification
(
	token varchar(128) not null,
	verification_number varchar(20) not null,
	verification_media varchar(32) not null,
	mobile_number varchar(64) null,
	email varchar(128) null,
	reg_datetime datetime default (CURRENT_TIMESTAMP) not null,
	primary key (token, verification_number)
);

create table if not exists virtual_number
(
	virtual_number varchar(20) not null
		primary key,
	type varchar(32) default 'normal' not null,
	reserved enum('Y', 'N') default 'N' not null,
	open_bounds varchar(32) null,
	action_source varchar(32) not null,
	actor_login_id varchar(64) not null,
	action_datetime datetime default (CURRENT_TIMESTAMP) not null,
	reserved_datetime datetime null,
	note text null,
	reserved_title varchar(128) null,
	reserved_reason text null,
	reserved_description varchar(256) null,
	number_prop varchar(4096) null,
	deleted enum('Y', 'N') default 'N' not null
)
charset=utf8;

create table if not exists event
(
	seq_no bigint auto_increment
		primary key,
	title varchar(256) not null,
	status varchar(32) not null,
	primary_type varchar(32) default 'insert' not null,
	secondary_type varchar(32) default 'none' not null,
	win_announce_type varchar(32) default 'none' not null,
	start_datetime datetime not null,
	end_datetime datetime not null,
	display_start_datetime datetime not null,
	display_end_datetime datetime not null,
	android enum('Y', 'N') default 'Y' not null,
	ios enum('Y', 'N') default 'Y' not null,
	contents_type varchar(32) null,
	contents text null,
	join_type varchar(32) default 'daily' not null,
	join_limit_count int default 1 not null,
	reward int default 0 not null,
	gift enum('Y', 'N') default 'Y' not null,
	lotto_match_num int null comment '로또(Lotto) 이벤트 번호 맞춤 갯수',
	biz_type varchar(32) default 'none' not null,
	display_user_app enum('Y', 'N') default 'Y' not null,
	display_biz_app enum('Y', 'N') default 'Y' not null,
	advertise_type varchar(32) default 'all' not null,
	advertise_reg_type varchar(32) default 'none' not null,
	advertise_reg_count int default 0 not null,
	advertise_pay int default 0 not null,
	biz_image_seq_no bigint null,
	target_type varchar(32) default 'all' not null,
	join_count int default 0 not null,
	min_join_count int default 0 not null,
	max_join_count int default 0 not null,
	winner_count int default 0 not null,
	total_gift_count int default 0 not null,
	code varchar(32) not null,
	display_time_type varchar(32) null,
	path varchar(32) null,
	move_type varchar(32) default 'none' not null,
	move_target_string varchar(512) null,
	move_target_number bigint null,
	win_announce_datetime datetime null,
	winner_desc text null,
	win_push_type varchar(32) default 'none' not null,
	win_push_title varchar(256) null,
	win_push_body text null,
	win_image_seq_no bigint null,
	win_detail_image_no bigint null,
	move_method varchar(32) default 'none' not null,
	winner_alert varchar(512) null,
	win_select_type varchar(32) default 'none' not null,
	win_desc_type varchar(32) default 'separate' not null,
	win_desc_reg_type varchar(32) default 'html' not null,
	banner_seq_no bigint null,
	detail_seq_no bigint null,
	virtual_number varchar(20) null,
	reward_type varchar(32) default 'none' not null,
	banner_pv int default 0 not null,
	banner_uv int default 0 not null,
	event_prop varchar(2048) null,
	etc varchar(2048) null,
	priority int default 1 not null,
	man enum('Y', 'N') default 'Y' not null,
	woman enum('Y', 'N') default 'Y' not null,
	`10age` enum('Y', 'N') default 'Y' not null,
	`20age` enum('Y', 'N') default 'Y' not null,
	`30age` enum('Y', 'N') default 'Y' not null,
	`40age` enum('Y', 'N') default 'Y' not null,
	`50age` enum('Y', 'N') default 'Y' not null,
	`60age` enum('Y', 'N') default 'Y' not null,
	married enum('Y', 'N') default 'Y' not null,
	not_married enum('Y', 'N') default 'Y' not null,
	hasChild enum('Y', 'N') default 'Y' not null,
	noChild enum('Y', 'N') default 'Y' not null,
	all_address enum('Y', 'N') default 'Y' not null,
	reg_datetime datetime default (CURRENT_TIMESTAMP) not null,
	group_seq_no bigint null,
	win_code varchar(32) null,
	lotto_times int null comment '로또 이벤트인 경우 회차 정보',
	lotto_price bigint null comment '로또(Lotto) 이벤트 당첨금액',
	guess_join_count int null comment '예상 참여 인원',
	is_batch tinyint(1) default 0 not null comment '일괄 발표',
	contents2 varchar(255) null comment '외부링크',
	cms enum('Y', 'N') default 'Y' not null comment 'cms 배포 여부',
	pcweb enum('Y', 'N') default 'Y' not null comment 'pcweb 배포 여부',
	mobileweb enum('Y', 'N') default 'Y' not null comment 'mobileweb 배포 여부',
	electron enum('Y', 'N') default 'Y' not null comment 'cms 배포 여부',
	page_seq_no bigint null comment '이벤트 상점 Page 순번',
	constraint event_ibfk_1
		foreign key (win_image_seq_no) references attachment (seq_no),
	constraint event_ibfk_2
		foreign key (banner_seq_no) references attachment (seq_no),
	constraint event_ibfk_3
		foreign key (detail_seq_no) references attachment (seq_no),
	constraint event_ibfk_4
		foreign key (virtual_number) references virtual_number (virtual_number),
	constraint event_ibfk_5
		foreign key (win_detail_image_no) references attachment (seq_no),
	constraint event_ibfk_6
		foreign key (biz_image_seq_no) references attachment (seq_no),
	constraint event_ibfk_7
		foreign key (group_seq_no) references eventgroup (seq_no)
)
charset=utf8;

create index R_entgrou_ent_4c6
	on event (group_seq_no);

create index R_tachmen_ent_472
	on event (win_detail_image_no);

create index R_tachmen_ent_dc8
	on event (biz_image_seq_no);

create index fk_event_attach_1
	on event (win_image_seq_no);

create index fk_event_attach_2
	on event (banner_seq_no);

create index fk_event_attach_3
	on event (detail_seq_no);

create index fk_event_virt_num
	on event (virtual_number);

create index lotto_times
	on event (lotto_times);

create index page_seq_no
	on event (page_seq_no);

create index page_seq_no_2
	on event (page_seq_no);

create index win_select_type
	on event (win_select_type);

create table if not exists event_address
(
	event_seq_no bigint not null,
	address varchar(128) not null,
	primary key (event_seq_no, address),
	constraint event_address_ibfk_1
		foreign key (event_seq_no) references event (seq_no)
);

create table if not exists event_advertise
(
	event_seq_no bigint not null,
	ad_seq_no bigint not null,
	reg_datetime datetime default (CURRENT_TIMESTAMP) not null,
	primary key (event_seq_no, ad_seq_no),
	constraint event_advertise_ibfk_1
		foreign key (event_seq_no) references event (seq_no),
	constraint event_advertise_ibfk_2
		foreign key (ad_seq_no) references advertise (seq_no)
);

create index fk_evt_ad_idx2
	on event_advertise (ad_seq_no);

create table if not exists event_banner
(
	event_seq_no bigint not null,
	seq_no int not null,
	title varchar(256) null,
	move_type varchar(32) default 'none' not null,
	move_target_string varchar(512) null,
	move_target_number bigint null,
	image_seq_no bigint null,
	pv int default 0 not null,
	uv int default 0 not null,
	priority int default 1 not null,
	primary key (event_seq_no, seq_no),
	constraint event_banner_ibfk_1
		foreign key (event_seq_no) references event (seq_no),
	constraint event_banner_ibfk_2
		foreign key (image_seq_no) references attachment (seq_no)
);

create index fk_event_banner_2
	on event_banner (image_seq_no);

create table if not exists event_banner_pv
(
	event_seq_no bigint not null,
	banner_seq_no int not null,
	seq_no int not null,
	member_seq_no bigint null,
	reg_datetime datetime default (CURRENT_TIMESTAMP) not null,
	primary key (event_seq_no, banner_seq_no, seq_no),
	constraint event_banner_pv_ibfk_1
		foreign key (event_seq_no, banner_seq_no) references event_banner (event_seq_no, seq_no),
	constraint event_banner_pv_ibfk_2
		foreign key (member_seq_no) references member (seq_no)
);

create index fk_event_banner_pv_2
	on event_banner_pv (member_seq_no);

create table if not exists event_banner_uv
(
	event_seq_no bigint not null,
	banner_seq_no int not null,
	member_seq_no bigint not null,
	give_reward enum('Y', 'N') default 'Y' not null,
	reg_datetime datetime default (CURRENT_TIMESTAMP) not null,
	primary key (event_seq_no, banner_seq_no, member_seq_no),
	constraint event_banner_uv_ibfk_1
		foreign key (event_seq_no, banner_seq_no) references event_banner (event_seq_no, seq_no),
	constraint event_banner_uv_ibfk_2
		foreign key (member_seq_no) references member (seq_no)
);

create index fk_event_banner_uv_2
	on event_banner_uv (member_seq_no);

create table if not exists event_category
(
	event_seq_no bigint not null,
	category_seq_no bigint not null,
	primary key (event_seq_no, category_seq_no),
	constraint fk_evt_cate_fkey1
		foreign key (event_seq_no) references event (seq_no),
	constraint fk_evt_cate_fkey2
		foreign key (category_seq_no) references category (seq_no)
);

create index fk_evt_cate_idx2
	on event_category (category_seq_no);

create table if not exists event_gift
(
	event_seq_no bigint not null,
	seq_no int not null,
	gift_type varchar(32) default 'goods' not null,
	title varchar(256) not null,
	total_count int not null,
	alert varchar(512) null,
	lot_percent double not null,
	price bigint not null,
	attach_seq_no bigint null,
	remain_count int not null,
	win_order varchar(512) null,
	beta_code varchar(12) null,
	primary key (event_seq_no, seq_no),
	constraint event_gift_ibfk_1
		foreign key (event_seq_no) references event (seq_no),
	constraint event_gift_ibfk_2
		foreign key (attach_seq_no) references attachment (seq_no)
);

create index fk_event_gift_attach
	on event_gift (attach_seq_no);

create table if not exists event_job
(
	event_seq_no bigint not null,
	job varchar(64) not null,
	primary key (event_seq_no, job),
	constraint event_job_ibfk_1
		foreign key (event_seq_no) references event (seq_no)
);

create table if not exists event_join
(
	event_seq_no bigint not null,
	seq_no int not null,
	member_seq_no bigint not null,
	join_datetime datetime default (CURRENT_TIMESTAMP) not null,
	join_prop varchar(4096) null,
	win_code varchar(32) null comment '예상 당첨번호 입력',
	primary key (event_seq_no, seq_no),
	constraint event_join_ibfk_1
		foreign key (event_seq_no) references event (seq_no),
	constraint event_join_ibfk_2
		foreign key (member_seq_no) references member (seq_no)
);

create index fk_event_join_2
	on event_join (member_seq_no);

create table if not exists event_page
(
	event_seq_no bigint not null,
	page_seq_no bigint not null,
	primary key (event_seq_no, page_seq_no),
	constraint fk_evt_pg_fkey1
		foreign key (event_seq_no) references event (seq_no),
	constraint fk_evt_pg_fkey2
		foreign key (page_seq_no) references page (seq_no)
);

create index fk_evt_pg_idx2
	on event_page (page_seq_no);

create table if not exists event_target
(
	event_seq_no bigint not null,
	member_seq_no bigint not null,
	primary key (event_seq_no, member_seq_no),
	constraint event_target_ibfk_1
		foreign key (event_seq_no) references event (seq_no),
	constraint event_target_ibfk_2
		foreign key (member_seq_no) references member (seq_no)
);

create index R_mber_ent_ce7
	on event_target (member_seq_no);

create table if not exists event_time
(
	event_seq_no bigint not null,
	start_time char(4) not null,
	end_time char(4) not null,
	primary key (event_seq_no, start_time, end_time),
	constraint event_time_ibfk_1
		foreign key (event_seq_no) references event (seq_no)
);

create table if not exists event_win
(
	event_seq_no bigint not null,
	seq_no int not null,
	member_seq_no bigint not null,
	gift_seq_no int null,
	win_datetime datetime default (CURRENT_TIMESTAMP) not null,
	impression varchar(1024) null,
	blind enum('Y', 'N') default 'N' not null,
	status varchar(32) default 'pending' not null,
	amount bigint null comment '당첨번호 맞추기 또는 로또 이벤트 럭키 볼 실 당첨 금액',
	primary key (event_seq_no, seq_no),
	constraint event_win_ibfk_1
		foreign key (event_seq_no) references event (seq_no),
	constraint event_win_ibfk_2
		foreign key (member_seq_no) references member (seq_no),
	constraint event_win_ibfk_3
		foreign key (event_seq_no, gift_seq_no) references event_gift (event_seq_no, seq_no)
);

create index fk_event_win_2
	on event_win (member_seq_no);

create index fk_event_win_3
	on event_win (event_seq_no, gift_seq_no);

create table if not exists lotto_event
(
	seq_no bigint auto_increment comment '사용자 순번'
		primary key,
	lotto_times int not null comment '로또 회차',
	event_seq_no bigint not null comment '이벤트 순번',
	win_code varchar(32) null comment '로또 당첨 번호',
	status int default 0 not null comment '0 : 비활성, 1: 진행중, 2: 발표완료',
	reg_datetime datetime not null comment '등록 시각',
	mod_datetime datetime not null comment '등록 시각',
	constraint lotto_times
		unique (lotto_times, event_seq_no),
	constraint lotto_event_ibfk_1
		foreign key (event_seq_no) references event (seq_no)
);

create index event_seq_no
	on lotto_event (event_seq_no);

create index reg_datetime
	on lotto_event (reg_datetime);

create table if not exists page_virtual_number
(
	page_seq_no bigint not null,
	virtual_number varchar(20) not null,
	status varchar(32) default 'active' not null,
	start_datetime datetime null,
	end_datetime datetime null,
	primary key (page_seq_no, virtual_number),
	constraint virtual_number
		unique (virtual_number),
	constraint fk_page_virtual_number1
		foreign key (page_seq_no) references page (seq_no),
	constraint fk_page_virtual_number2
		foreign key (virtual_number) references virtual_number (virtual_number)
)
charset=utf8;

create index fk_page_virtual_number2_idx
	on page_virtual_number (virtual_number);

create table if not exists promotion_number
(
	virtual_number varchar(20) not null,
	promotion_seq_no bigint not null,
	primary key (virtual_number, promotion_seq_no),
	constraint promotion_number_ibfk_1
		foreign key (virtual_number) references virtual_number (virtual_number),
	constraint promotion_number_ibfk_2
		foreign key (promotion_seq_no) references promotion (seq_no)
)
charset=utf8;

create index fk_promotion_promotion
	on promotion_number (promotion_seq_no);

create table if not exists pushplan
(
	seq_no bigint auto_increment
		primary key,
	title varchar(128) not null,
	code varchar(32) null,
	status varchar(32) default 'ready' not null,
	move_type1 varchar(32) default 'inner' not null,
	move_type2 varchar(32) not null,
	move_target_number bigint null,
	contents varchar(8092) null,
	reserved_datetime datetime null,
	image_seq_no bigint null,
	etc varchar(2048) null,
	target_type varchar(32) not null,
	type varchar(32) default 'user' not null,
	move_target_string varchar(512) null,
	msg_seq_no bigint null,
	priority int default 1 not null,
	man enum('Y', 'N') default 'Y' not null,
	woman enum('Y', 'N') default 'Y' not null,
	`10age` enum('Y', 'N') default 'Y' not null,
	`20age` enum('Y', 'N') default 'Y' not null,
	`30age` enum('Y', 'N') default 'Y' not null,
	`40age` enum('Y', 'N') default 'Y' not null,
	`50age` enum('Y', 'N') default 'Y' not null,
	`60age` enum('Y', 'N') default 'Y' not null,
	married enum('Y', 'N') default 'Y' not null,
	not_married enum('Y', 'N') default 'Y' not null,
	hasChild enum('Y', 'N') default 'Y' not null,
	noChild enum('Y', 'N') default 'Y' not null,
	all_address enum('Y', 'N') default 'Y' not null,
	all_category enum('Y', 'N') null,
	all_job enum('Y', 'N') default 'Y' not null,
	store enum('Y', 'N') default 'Y' not null,
	person enum('Y', 'N') default 'Y' not null,
	android enum('Y', 'N') default 'Y' not null,
	ios enum('Y', 'N') default 'Y' not null,
	event_seq_no bigint null,
	registrant_seq_no bigint null,
	reg_datetime datetime default (CURRENT_TIMESTAMP) not null,
	constraint pushplan_ibfk_1
		foreign key (image_seq_no) references attachment (seq_no),
	constraint pushplan_ibfk_2
		foreign key (msg_seq_no) references msg (seq_no),
	constraint pushplan_ibfk_3
		foreign key (event_seq_no) references event (seq_no),
	constraint pushplan_ibfk_4
		foreign key (registrant_seq_no) references member (seq_no)
);

create index fk_pushplan_attach
	on pushplan (image_seq_no);

create index fk_pushplan_event
	on pushplan (event_seq_no);

create index fk_pushplan_member
	on pushplan (registrant_seq_no);

create index fk_pushplan_msg
	on pushplan (msg_seq_no);

create table if not exists pushplan_address
(
	plan_seq_no bigint not null,
	address varchar(128) not null,
	primary key (plan_seq_no, address),
	constraint pushplan_address_ibfk_1
		foreign key (plan_seq_no) references pushplan (seq_no)
);

create table if not exists pushplan_category
(
	plan_seq_no bigint not null,
	category_seq_no bigint not null,
	primary key (plan_seq_no, category_seq_no),
	constraint pushplan_category_ibfk_1
		foreign key (plan_seq_no) references pushplan (seq_no),
	constraint pushplan_category_ibfk_2
		foreign key (category_seq_no) references category (seq_no)
);

create index fk_pushplan_cat_category
	on pushplan_category (category_seq_no);

create table if not exists pushplan_job
(
	plan_seq_no bigint not null,
	job varchar(64) not null,
	primary key (plan_seq_no, job),
	constraint pushplan_job_ibfk_1
		foreign key (plan_seq_no) references pushplan (seq_no)
);

create table if not exists pushplan_member
(
	plan_seq_no bigint not null,
	member_seq_no bigint not null,
	primary key (plan_seq_no, member_seq_no),
	constraint pushplan_member_ibfk_1
		foreign key (plan_seq_no) references pushplan (seq_no),
	constraint pushplan_member_ibfk_2
		foreign key (member_seq_no) references member (seq_no)
);

create index fk_pushplan_mbr_member
	on pushplan_member (member_seq_no);

create table if not exists virtual_number_length
(
	length tinyint not null
		primary key,
	status varchar(32) default 'active' not null,
	auto_mapping enum('Y', 'N') default 'Y' not null,
	mod_date datetime default (CURRENT_TIMESTAMP) not null,
	modifier_seq_no bigint not null
);

create table if not exists woy
(
	id int null
);

