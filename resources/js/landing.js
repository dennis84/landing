$(function() {
  $('body > :first-child,h1,h2').each(function(){ 
    $(this).nextUntil("h1,h2").andSelf().wrapAll('<section><div class="container"></section>')
  });
});
