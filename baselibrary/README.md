# BaseLibrary

- 앱의 기본 베이스 모음
- MVVM 패턴을 기본으로 한다.

# 적용방법

1. 신규 프로젝트 생성
2. File -> New -> Import Module...
3. 해당 폴더 지정 후 추가
4. 프로젝트 build.gradle에 apply from: 'baselibrary/versions.gradle' 추가
5. 프로젝트의 build.gradle들의 dependencies들을 versions.gradle이 지정한 값으로 수정 후 사용.(baslibrary/build.gradle 참조)
6. app/build.gradle에서 dependencies에 implementation project(":baselibrary") 추가. 클래스 사용 가능.
