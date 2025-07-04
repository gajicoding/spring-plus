# 플러스 주차 개인 과제



## AWS 인프라 구성 요약

### EC2
- 탄력적 IP: `3.34.211.143`
- 보안 그룹: `8080`, `22`, `80`, `443` 허용
- [✔️] health check API: [http://3.34.211.143:8080/health](http://3.34.211.143:8080/health)

![img.png](images/ec2.png)
![img.png](images/eip.png)

<br>

### RDS
- 엔드포인트: `db-mysql.cn8464wg8v6z.ap-northeast-2.rds.amazonaws.com`
- MySQL 8.0 / 포트 3306
- EC2에서 연결 허용

![img.png](images/rds.png)

<br>

### S3
- 버킷 이름: `gajicoding-spring-bucket`
- 프로필 이미지 수정 API:
    - `POST /users/profile-image`

![img.png](images/s3.png)

<br>
<br>

## 대용량 데이터 처리
- 데이터 준비

![img.png](images/insertUsers.png)

<br>

- 개선 전

![img.png](images/beforeAPI.png)
![img.png](images/beforeQuery.png)
![img_1.png](images/beforeExplain.png)

<br>

- INDEX 적용

![img.png](images/indexAPI.png)
![img.png](images/indexQuery.png)
![img.png](indexExplain.png)

<br>

- Projection 최적화 + Covering Index 사용

![img.png](images/projectionAPI.png)
![img.png](images/projectionQuery.png)
![img.png](ProjectionExplain.png)
