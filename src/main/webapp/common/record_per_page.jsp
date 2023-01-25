<form action="ReadCountries">
  <input type="hidden" name="currentPage" value="1">
  <div class="table">
    <label for="records">Select records per page:</label>
    <select class="form-control" id="records" name="recordsPerPage">
      <option value="5">5</option>
      <option value="10" selected>10</option>
      <option value="15">15</option>
    </select>
  </div>
  <button type="submit" class="btn btn-primary">Submit</button>
</form>