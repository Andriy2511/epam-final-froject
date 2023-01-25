<form name="changeLanguage">
  <select  class="form-select form-select-sm" id="language" name="language" onchange="submit()" >
    <option value="en_US" ${language == 'en_US' ? 'selected' : ''} value="english" >ENG</option>
    <option value="uk_UA" ${language == 'uk_UA' ? 'selected' : ''} value="ukrainian" >UKR</option>
  </select>
</form>