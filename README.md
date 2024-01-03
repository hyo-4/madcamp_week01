# 👟 FitTrack


### 운동과 식단을 기록하는 건강관리 어플


<p align="center">
<img src="https://github.com/hyo-4/madcamp_week01/assets/70904075/3e93cd05-dbfd-4a0a-a15c-291382cc8cce" width="300px" height="300px">
</p>



## 🚀 팀원
<br />

|백승효(숙명여대 20) | 황승찬(카이스트 22)|
|:-:|:-:|
|<img src="https://github.com/hyo-4/madcamp_week01/assets/70904075/3de28ee5-a91e-47a2-84dc-33d708f2de3a" width=150>|<img src="https://github.com/hyo-4/madcamp_week01/assets/110585202/a45c2b1b-e31d-4f37-82a4-627e46510d28" width=150>|
|[@hyo-4](https://github.com/hyo-4)|[@chanee718](https://github.com/chanee718)|

<br />

## ⚡️ 기술 스택

<img src="https://img.shields.io/badge/AndroidStudio-3DDC84?style=for-the-badge&logo=AndroidStudio&logoColor=white"> <img src="https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=Kotlin&logoColor=white">  ![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=Git&logoColor=white)  ![Github](https://img.shields.io/badge/Github-181717?style=for-the-badge&logo=Github&logoColor=white) ![Notion](https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=Notion&logoColor=white)


<br />

## 🗓 프로젝트 기간
> 2023.12.28 ~ 2022.1.03

백승효 : 갤러리, 갤러리 세부페이지, 갤러리 사진 수정, 연락처 검색, 캘린더 식단 & 운동 등록 및 수정, SplashScreen
<br />
황승찬 : 연락처 (추가, 수정, 상세 페이지, 삭제) 구현, 캘린더 탭 기능 (날짜 별로 운동 및 식단 사진 Bottom sheet을 통해 표시), Room DB 구축, 각 페이지 디자인

## 🚀 데모

<br />


|앱 실행 화면|연락처 메인페이지|연락처 등록 페이지|
|------|---|---|
|<img src="https://github.com/hyo-4/madcamp_week01/assets/70904075/15f604a4-df70-4b55-9305-f16ada19e3f3" width="300px">|<img src="https://github.com/hyo-4/madcamp_week01/assets/110585202/1bf37e89-18ac-4e90-83c5-04971caf675d" width="300px">|<img src="https://github.com/hyo-4/madcamp_week01/assets/70904075/c4eb1302-bafe-4f63-a5b0-f297a5ba550a" width="300px">|
|연락처 검색 | 갤러리 메인페이지|갤러리 상세페이지|
|<img src="https://github.com/hyo-4/madcamp_week01/assets/110585202/36efd369-b78d-4a53-be20-98ff3ea0fb3f" width="300px">|<img src="https://github.com/hyo-4/madcamp_week01/assets/70904075/317f8925-c215-439a-8918-c13280d65976" width="300px">|<img src="https://github.com/hyo-4/madcamp_week01/assets/70904075/a77c5f6e-b741-4b3b-9ea6-cedb01f6c9ad" width="300px">|
|갤러리 사진 수정페이지| 캘린더 메인페이지 | 캘린더 bottomup |
|<img src="https://github.com/hyo-4/madcamp_week01/assets/70904075/8e4b8ad5-12af-4840-9f93-b638bfd9f859" width="300px">|<img src="https://github.com/hyo-4/madcamp_week01/assets/110585202/8b719f45-8476-4191-a7ff-7f73aac3135c" width="300px">|<img src="https://github.com/hyo-4/madcamp_week01/assets/110585202/79736329-612c-4595-9a4b-fd16a7de4c90" width="300px">|
| 캘린더 식단 등록페이지 | 캘린더 운동 등록페이지
|<img src="https://github.com/hyo-4/madcamp_week01/assets/70904075/ed6b5970-69ac-472a-8cfe-08436da75207" width="300px">|<img src="https://github.com/hyo-4/madcamp_week01/assets/70904075/202d1870-244a-418a-b80b-78d93c5c182e" width="300px">


**🙍‍♂️ 연락처 탭( 메인, 상세 페이지, 등록, 수정, 삭제, 검색)**
- 저장한 연락처를 이름 순서대로 불러올 수 있는 페이지
- 연락처에는 프로필 사진, 이름, 태그, 전화번호를 저장
- 태그는 크게 3가지로, 친구, 운동 메이트, 그리고 헬스 트레이너로 분류
- 연락처 상세 페이지에서는 전화, 메세지, 영상통화 기능을 지원

**🖼️ 갤러리 탭( 메인, 디테일, 파일업로드 )**

- 캘린더에서 저장해온 식단과 운동 데이터들 중 이미지를 모아볼 수 있는 페이지.
- 처음 메인 화면에서는 최근순으로 전체 이미지를 불러옴.
- 아령 버튼을 누르면 운동 사진만 필터링 되고, 식단 필터링 버튼을 누르면 식단 사진만 필터링
- 갤러리에서 사진을 누르면 사진 디테일 창이 팝업됨. (팝업창에는 이미지와 해당 사진의 타입, 날짜등이 보여짐.)
- 사진 디테일 팝업에서 편집 버튼을 누르면 사진 수정 가능.

**🗓️ 캘린더 탭( 메인, bottomup, 식단 등록, 운동 등록)**
- 날짜 별로 하루의 식단과 운동한 기록을 남길 수 있는 페이지
- 캘린더에서 날짜를 선택하면 Bottom sheet에서 날짜 별 데이터를 조회 가능
- Bottom sheet를 끌어올리면 식단과 운동 사진 조회 가능
- 운동 등록 시 어떤 운동을 얼마나 했는지 저장
- 이미지를 클릭시 해당 항목에 대한 수정창으로 이동
- 캘린더 내 날짜별 식단 or 운동 데이터 추가 및 수정 가능

</br>
</br>



