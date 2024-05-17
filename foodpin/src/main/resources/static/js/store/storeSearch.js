 
 
 
function storelocation() {
    var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
        mapOption = { 
            center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
            level: 3 // 지도의 확대 레벨 
        }; 

    var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다



    function detaillocation(){

        
        // HTML5의 geolocation으로 사용할 수 있는지 확인
        if (navigator.geolocation) {
            try {
                // GeoLocation을 이용해서 접속 위치를 얻어옴
                const position =  new Promise ((resolve, reject) => {
                    navigator.geolocation.getCurrentPosition(resolve, reject);
                });

                position.then(resp => {
                    var lat = resp.coords.latitude, // 위도
                        lon = resp.coords.longitude; // 경도
                    
                    var locPosition = new kakao.maps.LatLng(lat, lon), // 마커가 표시될 위치를 geolocation으로 얻어온 좌표로 생성
                        message = '<div style="padding:5px;">현재 위치</div>'; // 인포윈도우에 표시될 내용
                    
                    // 마커와 인포윈도우를 표시
                    displayMarker(locPosition, message);

                })
                
            } catch (error) {
                console.error('Error getting geolocation:', error);
                var locPosition = new kakao.maps.LatLng(33.450701, 126.570667),
                    message = 'geolocation을 사용할수 없어요..';
                displayMarker(locPosition, message);
            }
        } else { // HTML5의 GeoLocation을 사용할 수 없을때 마커 표시 위치와 인포윈도우 내용을 설정
            var locPosition = new kakao.maps.LatLng(33.450701, 126.570667),    
                message = 'geolocation을 사용할수 없어요..'
            displayMarker(locPosition, message);
        }
   
        

    };


    detaillocation();



    // 화면 크기가 변경될 때마다 지도 중심을 유지
    window.addEventListener('resize', function() {
          
    
            
        detaillocation();
       
    
            /* 지도 크기 줄이면  */
          mapContainer.style.height = map.innerHeight + 'px';
          mapContainer.style.width = map.innerWidth + 'px';
        
            // 지도를 재배치하여 크기를 적용
            map.relayout();
        
            // 지도의 중심을 현재 중심으로 다시 설정
            var center = map.getCenter();
    
            
            map.setCenter(center);






        });


        
             // 지도에 마커와 인포윈도우를 표시하는 함수
       function displayMarker(locPosition, message) {
        /* 나중에 현재 위치는 마커 지우고 근처 가게들에 마커 생성 */

        // 마커를 생성
        var marker = new kakao.maps.Marker({  
            map: map, 
            position: locPosition
        }); 

        var iwContent = message, // 인포윈도우에 표시할 내용
            iwRemoveable = true;

        // 인포윈도우를 생성
        var infowindow = new kakao.maps.InfoWindow({
            content : iwContent,
            removable : iwRemoveable
        });

        // 인포윈도우를 마커위에 표시 
        infowindow.open(map, marker);

        // 지도 중심좌표를 접속위치로 변경
        map.setCenter(locPosition);      
    }    

    


   

}


/* 화면 생성 시 자동 실행 */
window.onload = function() {
    // currentlocation 함수 실행
    storelocation();
    detaillocation();
   
};

 