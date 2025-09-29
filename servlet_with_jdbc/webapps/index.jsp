<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registration Form</title>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">

    <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>

</head>

<body style="background: url(img/background%20image.jpg); background-size: cover; background-attachment:fixed;">

     <div class="container">
        <div class="row">
            <div class = "col m6 offset-m3">

                <div class = "card">
                    <div class ="card-content">
                        <h3 style="margin-top:10px;" class="center-align">Register Here</h3>
                        <h5 id="message" class="center-align "></h5>

                        <div class= "form center-align">

                            <form action="<%= application.getContextPath() %>/Register" method="post" id="myform">
                                <input type="text" name="user_name" placeholder="Enter your name here">
                                <input type="email" name="user_email" placeholder="Enter your email here">
                                <input type="password" name="user_password" placeholder="Enter your password here">
                                <button class="btn pink darken-1" type="submit">Submit</button>
                            </form>

                        </div>

                        <div class="loader center-align" style="margin-top:10px; display:none">

                             <div class="preloader-wrapper big active">
                                  <div class="spinner-layer spinner-blue">
                                    <div class="circle-clipper left">
                                      <div class="circle"></div>
                                    </div><div class="gap-patch">
                                      <div class="circle"></div>
                                    </div><div class="circle-clipper right">
                                      <div class="circle"></div>
                                    </div>
                                  </div>

                                  <div class="spinner-layer spinner-red">
                                    <div class="circle-clipper left">
                                      <div class="circle"></div>
                                    </div><div class="gap-patch">
                                      <div class="circle"></div>
                                    </div><div class="circle-clipper right">
                                      <div class="circle"></div>
                                    </div>
                                  </div>

                                  <div class="spinner-layer spinner-yellow">
                                    <div class="circle-clipper left">
                                      <div class="circle"></div>
                                    </div><div class="gap-patch">
                                      <div class="circle"></div>
                                    </div><div class="circle-clipper right">
                                      <div class="circle"></div>
                                    </div>
                                  </div>

                                  <div class="spinner-layer spinner-green">
                                    <div class="circle-clipper left">
                                      <div class="circle"></div>
                                    </div><div class="gap-patch">
                                      <div class="circle"></div>
                                    </div><div class="circle-clipper right">
                                      <div class="circle"></div>
                                    </div>
                                  </div>

                             </div>
                             <h5>Please wait...</h5>
                        </div>
                    </div>
                </div>

            </div>
        </div>
     </div>
<script
  src="https://code.jquery.com/jquery-3.7.1.min.js"
  integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo="
  crossorigin="anonymous"></script>

<script>
    $(document).ready(function(){
        console.log("page is ready...")

        $("#myform").on('submit', function(event){
            event.preventDefault();
            var formData = $(this).serialize();

            console.log(formData);
            $(".loader").show();
            $(".form").hide();

            $.ajax({
                url:"Register",
                data:formData,
                type:'POST',
                success: function(data, textStatus,  jqXHR){
                    console.log(data);
                    console.log("SUCCESS");
                    $(".loader").hide();
                    $(".form").show();

                    if(data.trim()==="Done"){
                        $("#message").html("Successfully Registered");
                        $("#message").addClass("green-text");
                        $("#myform")[0].reset();
                    }
                    else{
                        $("#message").stop(true,true);
                        $("#message").html("Something went wrong on server");
                        $("#message").addClass("red-text");
                    }
                },
                error: function(data, textStatus,  errorThrown){
                    console.log(data);
                    console.log("ERROR");
                    $(".loader").hide();
                    $(".form").show();
                    $("#message").html("Something went wrong on server");
                    $("#message").addClass("red-text");
                }
            })
        })
    })
</script>

</body>
</html>
