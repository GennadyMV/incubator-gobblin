/*
 * Copyright (C) 2014-2016 LinkedIn Corp. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the
 * License at  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied.
 */
package gobblin.runtime.api;

import java.net.URI;

import com.google.common.base.Objects;

import gobblin.annotation.Alpha;
import gobblin.util.callbacks.Callback;

/**
 *  A listener for changes to the {@link JobSpec}s of a {@link JobCatalog}.
 */
@Alpha
public interface JobCatalogListener {
  /** Invoked when a new JobSpec is added to the catalog and for all pre-existing jobs on registration
   * of the listener.*/
  public void onAddJob(JobSpec addedJob);

  /**
   * Invoked when a JobSpec gets removed from the catalog.
   */
  public void onDeleteJob(URI deletedJobURI, String deletedJobVersion);

  /**
   * Invoked when the contents of a JobSpec gets updated in the catalog.
   */
  public void onUpdateJob(JobSpec updatedJob);

  /** A standard implementation of onAddJob as a functional object */
  public static class AddJobCallback extends Callback<JobCatalogListener, Void> {
    private final JobSpec _addedJob;
    public AddJobCallback(JobSpec addedJob) {
      super(Objects.toStringHelper("onAddJob").add("addedJob", addedJob).toString());
      _addedJob = addedJob;
    }

    @Override public Void apply(JobCatalogListener listener) {
      listener.onAddJob(_addedJob);
      return null;
    }
  }

  /** A standard implementation of onDeleteJob as a functional object */
  public static class DeleteJobCallback extends Callback<JobCatalogListener, Void> {
    private final URI _deletedJobURI;
    private final String _deletedJobVersion;

    public DeleteJobCallback(URI deletedJobURI, String deletedJobVersion) {
      super(Objects.toStringHelper("onDeleteJob")
                   .add("deletedJobURI", deletedJobURI)
                   .add("deletedJobVersion", deletedJobVersion)
                   .toString());
      _deletedJobURI = deletedJobURI;
      _deletedJobVersion = deletedJobVersion;
    }

    @Override public Void apply(JobCatalogListener listener) {
      listener.onDeleteJob(_deletedJobURI, _deletedJobVersion);
      return null;
    }
  }

  public static class UpdateJobCallback extends Callback<JobCatalogListener, Void> {
    private final JobSpec _updatedJob;
    public UpdateJobCallback(JobSpec updatedJob) {
      super(Objects.toStringHelper("onUpdateJob")
                   .add("updatedJob", updatedJob).toString());
      _updatedJob = updatedJob;
    }

    @Override
    public Void apply(JobCatalogListener listener) {
      listener.onUpdateJob(_updatedJob);
      return null;
    }
  }

}